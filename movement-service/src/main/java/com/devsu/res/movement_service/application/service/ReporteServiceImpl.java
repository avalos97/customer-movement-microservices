package com.devsu.res.movement_service.application.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.devsu.res.movement_service.adapter.outbound.client.CustomerServiceClient;
import com.devsu.res.movement_service.application.dto.MovementReportDTO;
import com.devsu.res.movement_service.application.dto.MovimientoResponseDTO;
import com.devsu.res.movement_service.application.dto.ReporteEstadoCuentaDTO;
import com.devsu.res.movement_service.application.dto.client.CustomerResponseDTO;
import com.devsu.res.movement_service.application.usecase.ReporteUseCase;
import com.devsu.res.movement_service.common.constant.ErrorCode;
import com.devsu.res.movement_service.common.mapper.BaseMapper;
import com.devsu.res.movement_service.domain.exception.MovimientoNotFoundException;
import com.devsu.res.movement_service.domain.model.Cuenta;
import com.devsu.res.movement_service.domain.model.Movimiento;
import com.devsu.res.movement_service.domain.port.outbound.CuentaRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReporteServiceImpl extends BaseMapper<Movimiento, MovimientoResponseDTO> implements ReporteUseCase {

    private final CuentaRepositoryPort cuentaRepositoryPort;
    private final CustomerServiceClient customerServiceClient;

@Override
public ReporteEstadoCuentaDTO generarReporteEstadoCuenta(LocalDate initDate, LocalDate endDate, UUID clientId) {
    LocalDateTime inicio = initDate.atStartOfDay();
    LocalDateTime fin = endDate.atTime(23, 59, 59);
    
    List<Cuenta> cuentas = cuentaRepositoryPort.findAccountsWithMovementsByClientAndDate(clientId, inicio, fin);
    List<ReporteEstadoCuentaDTO.CuentaReporteDTO> cuentasReporte = cuentas.stream().map(cuenta -> {
        List<MovementReportDTO> movimientosDto = cuenta.getMovimientos().stream()
            .sorted(Comparator.comparing(Movimiento::getFecha))
            .map(mov -> modelMapper.map(mov, MovementReportDTO.class))
            .collect(Collectors.toList());
        ReporteEstadoCuentaDTO.CuentaReporteDTO cuentaReporte = new ReporteEstadoCuentaDTO.CuentaReporteDTO();
        cuentaReporte.setCuentaId(cuenta.getId());
        cuentaReporte.setNumeroCuenta(cuenta.getNumeroCuenta());
        cuentaReporte.setTipoCuenta(cuenta.getTipoCuenta());
        cuentaReporte.setSaldoInicial(cuenta.getSaldoInicial());
        cuentaReporte.setMovimientos(movimientosDto);
        return cuentaReporte;
    }).collect(Collectors.toList());

    if (cuentas.isEmpty()) {
        throw new MovimientoNotFoundException(ErrorCode.MOVEMENT_NOT_FOUND, "No se encontraron movimientos para el cliente en el rango de fechas especificado");
    }

    CustomerResponseDTO customer = customerServiceClient.getClienteById(clientId);

    ReporteEstadoCuentaDTO reporte = new ReporteEstadoCuentaDTO();
    reporte.setClienteId(clientId);
    reporte.setClienteNombre(customer.getNombre());
    reporte.setCuentas(cuentasReporte);
    return reporte;
}


    @Override
    protected Class<Movimiento> getEntityClass() {
        return Movimiento.class;
    }

    @Override
    protected Class<MovimientoResponseDTO> getDtoClass() {
        return MovimientoResponseDTO.class;
    }
}