package com.wallet.constant;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

public enum ErrorType implements Serializable {

    USER_NOT_FOUND("error.user.not.found", HttpStatus.NOT_FOUND),
    TRANSACTION_ID_NOT_FOUND("error.transaction.not.found", HttpStatus.BAD_REQUEST),
    USER_ALREADY_EXISTS("error.user.already.exists", HttpStatus.BAD_REQUEST),
    INSUFFICIENT_AMOUNT("error.insufficient.amount", HttpStatus.BAD_REQUEST),
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
