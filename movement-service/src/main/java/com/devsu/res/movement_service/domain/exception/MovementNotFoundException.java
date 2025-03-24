package com.devsu.res.movement_service.domain.exception;

import com.devsu.res.movement_service.common.constant.ErrorCode;

/**
 * Excepción lanzada cuando no se encuentra un movimiento.
 */
public class MovementNotFoundException extends DomainException {

    private final ErrorCode errorCode;

    public MovementNotFoundException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}