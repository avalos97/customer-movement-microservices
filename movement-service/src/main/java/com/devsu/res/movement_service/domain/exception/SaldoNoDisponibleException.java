package com.devsu.res.movement_service.domain.exception;

import com.devsu.res.movement_service.common.constant.ErrorCode;

/**
 * Excepción lanzada cuando se intenta realizar un movimiento y no hay saldo
 * suficiente.
 */
public class SaldoNoDisponibleException extends DomainException {
    
    private final ErrorCode errorCode;

    public SaldoNoDisponibleException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
