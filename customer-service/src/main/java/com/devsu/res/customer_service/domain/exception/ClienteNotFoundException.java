package com.devsu.res.customer_service.domain.exception;

import com.devsu.res.customer_service.common.constant.ErrorCode;

public class ClienteNotFoundException extends DomainException {
    
    private final ErrorCode errorCode;
    
    public ClienteNotFoundException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}