package com.wallet.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallet.model.exception.CustomErrorResponse;
import com.wallet.model.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

import static com.wallet.constant.WalletConstants.ERROR_KEY;
import static com.wallet.constant.WalletConstants.INVALID_VALUE_KEY;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ObjectMapper mapper;

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        log.error("Handling missing servlet parameter exception"+ex);
        String error = ex.getParameterName() + " parameter is missing";
        return buildResponseEntity(new CustomErrorResponse(error, BAD_REQUEST));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        log.error("Handling media type not supported exception"+ex);
        return buildResponseEntity(new CustomErrorResponse(" media type is not supported.", HttpStatus.UNSUPPORTED_MEDIA_TYPE));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        log.error("Handling method argument not valid exception"+ex);
        CustomErrorResponse customErrorResponse = new CustomErrorResponse("Invalid Input", BAD_REQUEST);
        Map<String, Map<String, Object>> fieldErrors = getAllArgumentNotValidErrors(ex);

        customErrorResponse.setDetails(fieldErrors);
        return buildResponseEntity(customErrorResponse);
    }

    private Map<String, Map<String, Object>> getAllArgumentNotValidErrors(MethodArgumentNotValidException exception) {
        Map<String, Map<String, Object>> fieldErrors = new HashMap<>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            Map<String, Object> errorDetails;
            if (fieldErrors.containsKey(fieldError.getField())) {
                errorDetails = fieldErrors.get(fieldError.getField());
            } else {
                errorDetails = new HashMap<>();
                if(Optional.ofNullable(fieldError.getRejectedValue()).isPresent()) {
                    errorDetails.put(INVALID_VALUE_KEY, fieldError.getRejectedValue());
                }
                errorDetails.put(ERROR_KEY, new ArrayList<String>());
            }
            ArrayList<String> errorDetailsList = mapper.convertValue(errorDetails.get(ERROR_KEY),
                    new TypeReference<ArrayList<String>>() {
                    });
            errorDetailsList.add(fieldError.getDefaultMessage());
            errorDetails.put(ERROR_KEY, errorDetailsList);
            fieldErrors.put(fieldError.getField(), errorDetails);
        }
        return fieldErrors;
    }

   @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        log.info("{} to {}", servletWebRequest.getHttpMethod(), servletWebRequest.getRequest().getServletPath());
        String error = "Malformed JSON request";
        return buildResponseEntity(new CustomErrorResponse(error, HttpStatus.BAD_REQUEST));
    }


    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<Object> handleCustomException(
            CustomException ex) {
        log.error("Handling custom exception"+ex);
        String message = messageSource.getMessage(ex.getErrorType().getMessageKey(), null, Locale.getDefault());
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(message, ex.getErrorType().getHttpStatus());

        return buildResponseEntity(customErrorResponse);
    }


    private ResponseEntity<Object> buildResponseEntity(CustomErrorResponse customErrorResponse) {
        return new ResponseEntity<>(customErrorResponse, customErrorResponse.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> customHandler(Exception exception, WebRequest request) {
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return buildResponseEntity(customErrorResponse);
    }

}
