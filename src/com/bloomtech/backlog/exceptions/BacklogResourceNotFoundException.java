package com.bloomtech.backlog.exceptions;

public class BacklogResourceNotFoundException extends RuntimeException {
    public BacklogResourceNotFoundException(String message) {
        super(message);
    }

    public BacklogResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BacklogResourceNotFoundException(Throwable cause) {
        super(cause);
    }

    public BacklogResourceNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
