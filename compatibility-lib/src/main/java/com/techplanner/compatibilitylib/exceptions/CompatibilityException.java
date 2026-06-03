package com.techplanner.compatibilitylib.exceptions;

/**
 * Base exception for compatibility analysis failures.
 */
public class CompatibilityException extends RuntimeException {

    public CompatibilityException(String message) {
        super(message);
    }

    public CompatibilityException(String message, Throwable cause) {
        super(message, cause);
    }
}