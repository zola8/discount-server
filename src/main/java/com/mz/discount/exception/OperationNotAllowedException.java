package com.mz.discount.exception;

public class OperationNotAllowedException extends RuntimeException {

    public OperationNotAllowedException(final String text) {
        super(text);
    }

}
