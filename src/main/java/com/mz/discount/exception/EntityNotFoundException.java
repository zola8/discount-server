package com.mz.discount.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(final Long id, final Class clazz) {
        super("Entity (" + clazz + ") not found. ID: " + id);
    }

}
