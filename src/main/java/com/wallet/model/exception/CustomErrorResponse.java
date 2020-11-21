package com.wallet.model.exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

/**
 * Model for representing custom errors.
 */
@Data
@NoArgsConstructor
public class CustomErrorResponse {

    private static final long serialVersionUID = 2L;

    private ZonedDateTime timeStamp;
    private HttpStatus status;
    private String error;
    private Object details;

    public CustomErrorResponse(String errorMessage, HttpStatus httpStatus) {
        this.timeStamp = ZonedDateTime.now();
        this.error = errorMessage;
        this.status = httpStatus;
    }

}
