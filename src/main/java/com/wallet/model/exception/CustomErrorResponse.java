package com.wallet.model.exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
public class CustomErrorResponse {

    private static final long serialVersionUID = 2L;

    private ZonedDateTime timeStamp;
    private HttpStatus status;
    private String transactionStatus;
    private String error;
    private String path;
    private Object details;

    public CustomErrorResponse(String errorMessage, HttpStatus httpStatus) {
        this.timeStamp = ZonedDateTime.now();
        this.error = errorMessage;
        this.status = httpStatus;
    }

}
