package com.luca.film.film.exceptions;

public class OperationNotPermittedException extends  RuntimeException{
    public OperationNotPermittedException() {
        super();
    }

    public OperationNotPermittedException(String message) {
        super(message);
    }

    public OperationNotPermittedException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperationNotPermittedException(Throwable cause) {
        super(cause);
    }
}
