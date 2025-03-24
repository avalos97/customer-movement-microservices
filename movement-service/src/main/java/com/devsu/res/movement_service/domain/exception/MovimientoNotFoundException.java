package com.devsu.res.movement_service.domain.exception;

import com.devsu.res.movement_service.common.constant.ErrorCode;

/**
 * Excepción lanzada cuando no se encuentra un movimiento.
 */
public class MovimientoNotFoundException extends DomainException {

    private final ErrorCode errorCode;

    public MovimientoNotFoundException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}