package com.devsu.res.movement_service.domain.exception;

import com.devsu.res.movement_service.common.constant.ErrorCode;

/**
 * Excepción lanzada cuando no se encuentra una cuenta.
 */
public class CuentaNotFoundException extends DomainException {

    private final ErrorCode errorCode;

    public CuentaNotFoundException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}