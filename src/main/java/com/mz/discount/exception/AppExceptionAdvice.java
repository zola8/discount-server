package com.mz.discount.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppExceptionAdvice.class);

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String entityNotFoundHandler(EntityNotFoundException ex) {
        LOGGER.warn("{}", ex.getMessage());
        return ex.getMessage();
    }

    @ExceptionHandler(OperationNotAllowedException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public String notAcceptableOperationHandler(OperationNotAllowedException ex) {
        LOGGER.warn("Not acceptable operation. {}", ex.getMessage());
        return ex.getMessage();
    }

}
