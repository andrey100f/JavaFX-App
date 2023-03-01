package com.example.autoserviceapp.domain.validators;

/**
 * ValidationException class
 */
public class ValidationException extends RuntimeException {
    /**
     * Constructor with parameters
     * @param message String
     */
    public ValidationException(String message) {
        super(message);
    }
}
