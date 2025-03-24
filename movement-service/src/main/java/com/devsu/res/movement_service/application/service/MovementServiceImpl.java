package com.devsu.res.movement_service.application.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.devsu.res.movement_service.application.dto.MovementResponseDTO;
import com.devsu.res.movement_service.application.usecase.MovementUseCase;
import com.devsu.res.movement_service.common.constant.ErrorCode;
import com.devsu.res.movement_service.common.mapper.BaseMapper;
import com.devsu.res.movement_service.domain.exception.MovementNotFoundException;
import com.devsu.res.movement_service.domain.model.Movement;
import com.devsu.res.movement_service.domain.port.outbound.MovementRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovementServiceImpl extends BaseMapper<Movement, MovementResponseDTO> implements MovementUseCase {

    private final MovementRepositoryPort movementRepositoryPort;
    @Override
    public MovementResponseDTO getMovementById(UUID id) {
        Movement movement = movementRepositoryPort.findById(id)
                .orElseThrow(() -> new MovementNotFoundException(ErrorCode.RESOURCE_NOT_FOUND, ErrorCode.MOVEMENT_NOT_FOUND.getErrMsgKey() + id));
        return modelMapper.map(movement, MovementResponseDTO.class);
    }

    @Override
    public List<MovementResponseDTO> getMovementsByAccount(UUID accountId) {
        List<Movement> movements = movementRepositoryPort.findByAccountId(accountId);
        return movements.stream()
                .map(mov -> modelMapper.map(mov, MovementResponseDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    protected Class<Movement> getEntityClass() {
        return Movement.class;
    }

    @Override
    protected Class<MovementResponseDTO> getDtoClass() {
        return MovementResponseDTO.class;
    }
}
