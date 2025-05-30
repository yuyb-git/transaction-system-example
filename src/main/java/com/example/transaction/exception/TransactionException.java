package com.example.transaction.exception;

import com.example.transaction.global.GlobalConstant;

public class TransactionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String message;
    private int errorCode;

    public TransactionException(String message, int errorCode) {
        super(message);
        this.message = message;
        this.errorCode = errorCode;
    }

    public TransactionException(String message) {
        super(message);
        this.message = message;
        this.errorCode = GlobalConstant.FAIL;
    }

    public TransactionException(Throwable cause) {
        super(cause);
    }

    public TransactionException(String message, int errorCode, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
