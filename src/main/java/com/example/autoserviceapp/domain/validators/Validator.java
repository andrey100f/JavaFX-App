package com.example.autoserviceapp.domain.validators;

/**
 * Validator interface
 * @param <E> Entity object
 */
public interface Validator<E> {
    /**
     * Validate function
     * @param entity Entity object
     */
    void validate(E entity) throws ValidationException;
}
