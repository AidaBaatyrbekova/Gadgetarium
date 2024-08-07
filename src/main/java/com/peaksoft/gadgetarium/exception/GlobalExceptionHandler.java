package com.peaksoft.gadgetarium.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public  ExceptionResponse handleUserAlreadyExistsException(UserAlreadyExistsException exception) {
        return new ExceptionResponse(HttpStatus.BAD_REQUEST, exception.getMessage(), getClass().getName());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleNotFoundException(NotFoundException e) {
        return new ExceptionResponse(HttpStatus.NOT_FOUND, e.getMessage(), getClass().getName());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleGlobalException(Exception ex) {
        return new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), getClass().getName());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        return new ExceptionResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), getClass().getName());
    }
}