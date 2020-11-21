package com.wallet.model.exception;

import com.wallet.constant.ErrorType;

/**
 * Exception class for custom exceptions.
 */
public class CustomException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final ErrorType errorType;

    public CustomException(ErrorType errorType) {
        super(errorType.getMessageKey());
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return this.errorType;
    }

}
