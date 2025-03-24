package com.devsu.res.movement_service.domain.exception;

import com.devsu.res.movement_service.common.constant.ErrorCode;

/**
 * Excepción base para errores en el dominio.
 */
public class DomainException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private ErrorCode errorCode;

    public DomainException(String message) {
        super(message);
    }

    public DomainException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}