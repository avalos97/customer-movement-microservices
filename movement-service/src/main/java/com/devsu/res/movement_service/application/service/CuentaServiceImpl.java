package com.devsu.res.movement_service.application.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.devsu.res.movement_service.application.dto.CuentaRequestDTO;
import com.devsu.res.movement_service.application.dto.CuentaResponseDTO;
import com.devsu.res.movement_service.application.dto.MovimientoRequestDTO;
import com.devsu.res.movement_service.application.usecase.CuentaUseCase;
import com.devsu.res.movement_service.common.constant.ErrorCode;
import com.devsu.res.movement_service.common.mapper.BaseMapper;
import com.devsu.res.movement_service.common.util.DateUtil;
import com.devsu.res.movement_service.domain.exception.CuentaNotFoundException;
import com.devsu.res.movement_service.domain.exception.SaldoNoDisponibleException;
import com.devsu.res.movement_service.domain.model.Cuenta;
import com.devsu.res.movement_service.domain.model.Movimiento;
import com.devsu.res.movement_service.domain.port.outbound.CuentaRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CuentaServiceImpl extends BaseMapper<Cuenta, CuentaResponseDTO> implements CuentaUseCase {

    private final CuentaRepositoryPort cuentaRepositoryPort;

    @Override
    public CuentaResponseDTO createCuenta(CuentaRequestDTO requestDTO) {
        modelMapper.typeMap(CuentaRequestDTO.class, Cuenta.class)
                .addMappings(mapper -> mapper.skip(Cuenta::setId));
        Cuenta cuenta = modelMapper.map(requestDTO, Cuenta.class);
        cuenta.setEstado(true);
        Cuenta created = cuentaRepositoryPort.save(cuenta);
        return this.entityToDto(created);
    }

    @Override
    public CuentaResponseDTO updateCuenta(UUID id, CuentaRequestDTO requestDTO) {
        Cuenta existing = cuentaRepositoryPort.findById(id)
                .orElseThrow(() -> new CuentaNotFoundException(ErrorCode.RESOURCE_NOT_FOUND,
                        "Cuenta no encontrada con id: " + id));
        modelMapper.map(requestDTO, existing);
        Cuenta updated = cuentaRepositoryPort.save(existing);
        return this.entityToDto(updated);
    }

    @Override
    public CuentaResponseDTO getCuentaById(UUID id) {
        Cuenta cuenta = cuentaRepositoryPort.findById(id)
                .orElseThrow(() -> new CuentaNotFoundException(ErrorCode.RESOURCE_NOT_FOUND,
                        "Cuenta no encontrada con id: " + id));
        return this.entityToDto(cuenta);
    }

    @Override
    public void deleteCuenta(UUID id) {
        cuentaRepositoryPort.deleteById(id);
    }

    @Override
    public List<CuentaResponseDTO> getAllCuentas() {
        List<Cuenta> cuentas = cuentaRepositoryPort.findAll();
        return this.entityListToDtoList(cuentas);
    }

    @Override
    public CuentaResponseDTO registrarMovimiento(UUID cuentaId, MovimientoRequestDTO requestDTO) {
        Cuenta cuenta = cuentaRepositoryPort.findById(cuentaId)
                .orElseThrow(() -> new CuentaNotFoundException(ErrorCode.RESOURCE_NOT_FOUND,
                        "Cuenta no encontrada con id: " + cuentaId));
        requestDTO.setCuentaId(cuentaId);
        if (!cuenta.getEstado()) {
            throw new IllegalStateException("No se puede operar sobre una cuenta inactiva");
        }

        String movementType = requestDTO.getValor().compareTo(BigDecimal.ZERO) > 0 ? "DEPOSITO" : "RETIRO";
        requestDTO.setTipoMovimiento(movementType);

        BigDecimal saldoActual = (cuenta.getMovimientos() != null && !cuenta.getMovimientos().isEmpty())
                ? cuenta.getMovimientos().stream()
                        .filter(mov -> Boolean.TRUE.equals(mov.getEstado()))
                        .max(Comparator.comparing(Movimiento::getFecha))
                        .map(Movimiento::getSaldo)
                        .orElse(cuenta.getSaldoInicial())
                : cuenta.getSaldoInicial();

        Map<String, Function<BigDecimal, BigDecimal>> operations = Map.of(
                "RETIRO", valor -> {
                    var ammountPositive = valor.abs();
                    if (saldoActual.compareTo(ammountPositive) < 0) {
                        throw new SaldoNoDisponibleException(ErrorCode.BALANCE_NOT_AVAILABLE,
                                "Saldo no disponible para retiro");
                    }
                    return saldoActual.subtract(ammountPositive);
                },
                "DEPOSITO", valor -> saldoActual.add(valor));

        Function<BigDecimal, BigDecimal> operation = operations.get(requestDTO.getTipoMovimiento().toUpperCase());
        if (operation == null) {
            throw new IllegalArgumentException("Tipo de movimiento no válido: " + requestDTO.getTipoMovimiento());
        }

        // Calcular el nuevo saldo
        BigDecimal nuevoSaldo = operation.apply(requestDTO.getValor());

        modelMapper.typeMap(MovimientoRequestDTO.class, Movimiento.class)
                .addMappings(mapper -> mapper.skip(Movimiento::setId));
        Movimiento movimiento = modelMapper.map(requestDTO, Movimiento.class);
        movimiento.setSaldo(nuevoSaldo);
        movimiento.setEstado(true);
        movimiento.setFecha(DateUtil.now());

        Optional.ofNullable(cuenta.getMovimientos())
            .orElseGet(ArrayList::new)
            .add(movimiento);
            
        Cuenta updatedCuenta = cuentaRepositoryPort.save(cuenta);

       return this.entityToDto(updatedCuenta);
    }

    @Override
    protected Class<Cuenta> getEntityClass() {
        return Cuenta.class;
    }

    @Override
    protected Class<CuentaResponseDTO> getDtoClass() {
        return CuentaResponseDTO.class;
    }
}
