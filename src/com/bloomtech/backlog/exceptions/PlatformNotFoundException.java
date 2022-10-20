package com.bloomtech.backlog.exceptions;

public class PlatformNotFoundException extends BacklogResourceNotFoundException {
    public PlatformNotFoundException(String message) {
        super(message);
    }

    public PlatformNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlatformNotFoundException(Throwable cause) {
        super(cause);
    }

    public PlatformNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
