package com.devsu.res.customer_service.common.constant;

public enum ErrorCode {

    GENERIC_ERROR("PACKT-0001", "The system is unable to complete the request. Contact system support."),
    CONSTRAINT_VIOLATION("PACKT-0002", "Validation failed."),
    ILLEGAL_ARGUMENT_EXCEPTION("PACKT-0003", "Invalid data passed."),
    RESOURCE_NOT_FOUND("PACKT-0004", "Requested resource not found."),
    RECORD_NOT_FOUND("PACKT-0005", "Record not found - "),
    GENERIC_ALREADY_EXISTS("PACKT-0006", "Already exists."),
    INVALID_DATA("PACKT-0007", "Invalid data - "),
    TRANSACTION_ERROR("PACKT-0008", "Error in the requested transaction - ");

    private final String errCode;
    private final String errMsgKey;

    ErrorCode(String errCode, String errMsgKey) {
        this.errCode = errCode;
        this.errMsgKey = errMsgKey;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrMsgKey() {
        return errMsgKey;
    }
}
