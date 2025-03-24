package com.devsu.res.movement_service.domain.exception;

import com.devsu.res.movement_service.common.constant.ErrorCode;

/**
 * Excepción lanzada cuando se intenta realizar un movimiento y no hay saldo
 * suficiente.
 */
public class BalanceNotAvailableException extends DomainException {
    
    private final ErrorCode errorCode;

    public BalanceNotAvailableException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
