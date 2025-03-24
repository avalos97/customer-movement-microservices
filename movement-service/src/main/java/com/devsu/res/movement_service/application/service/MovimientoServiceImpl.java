package com.devsu.res.movement_service.application.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.devsu.res.movement_service.application.dto.MovimientoResponseDTO;
import com.devsu.res.movement_service.application.usecase.MovimientoUseCase;
import com.devsu.res.movement_service.common.constant.ErrorCode;
import com.devsu.res.movement_service.common.mapper.BaseMapper;
import com.devsu.res.movement_service.domain.exception.MovimientoNotFoundException;
import com.devsu.res.movement_service.domain.model.Movimiento;
import com.devsu.res.movement_service.domain.port.outbound.MovimientoRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovimientoServiceImpl extends BaseMapper<Movimiento, MovimientoResponseDTO> implements MovimientoUseCase {

    private final MovimientoRepositoryPort movimientoRepositoryPort;
    @Override
    public MovimientoResponseDTO getMovimientoById(UUID id) {
        Movimiento movimiento = movimientoRepositoryPort.findById(id)
                .orElseThrow(() -> new MovimientoNotFoundException(ErrorCode.RESOURCE_NOT_FOUND, ErrorCode.MOVEMENT_NOT_FOUND.getErrMsgKey() + id));
        return modelMapper.map(movimiento, MovimientoResponseDTO.class);
    }

    @Override
    public List<MovimientoResponseDTO> getMovimientosByCuenta(UUID cuentaId) {
        List<Movimiento> movimientos = movimientoRepositoryPort.findByCuentaId(cuentaId);
        return movimientos.stream()
                .map(mov -> modelMapper.map(mov, MovimientoResponseDTO.class))
                .collect(Collectors.toList());
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
