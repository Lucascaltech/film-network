package com.luca.film.film.exceptions;

/**
 * Exception thrown when a user attempts to perform an operation that is not permitted.
 */
public class OperationNotPermittedException extends  RuntimeException{
    
    /**
     * Constructs a new OperationNotPermittedException with the specified detail message.
     * @param message the detail message
     */
    public OperationNotPermittedException() {
        super();
    }

    /**
     * Constructs a new OperationNotPermittedException with the specified detail message.
     * @param message the detail message
     */
    public OperationNotPermittedException(String message) {
        super(message);
    }

    /**
     * Constructs a new OperationNotPermittedException with the specified detail message and cause.
     * @param message the detail message
     * @param cause the cause
     */
    public OperationNotPermittedException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new OperationNotPermittedException with the specified cause.
     * @param cause the cause
     */
    public OperationNotPermittedException(Throwable cause) {
        super(cause);
    }
}
