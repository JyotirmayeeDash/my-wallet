package com.wallet.constant;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * Enum that holds all the error types.
 */
public enum ErrorType implements Serializable {

    TRANSACTION_ID_NOT_FOUND("error.transaction.not.found", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTS("error.user.already.exists", HttpStatus.BAD_REQUEST),
    INSUFFICIENT_WALLET_BALANCE("error.insufficient.amount", HttpStatus.BAD_REQUEST),
    BANK_SERVER_ERROR("error.bank.server", HttpStatus.BAD_REQUEST);




    private final String messageKey;
    private final HttpStatus httpStatus;

    ErrorType(String messageKey, HttpStatus httpStatus) {
        this.messageKey = messageKey;
        this.httpStatus = httpStatus;

    }
    public String getMessageKey() {
        return this.messageKey;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
