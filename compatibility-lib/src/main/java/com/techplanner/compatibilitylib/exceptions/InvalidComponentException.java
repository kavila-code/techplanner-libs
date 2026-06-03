package com.techplanner.compatibilitylib.exceptions;

/**
 * Thrown when a component contains invalid or inconsistent data.
 */
public class InvalidComponentException extends CompatibilityException {

    public InvalidComponentException(String message) {
        super(message);
    }
}