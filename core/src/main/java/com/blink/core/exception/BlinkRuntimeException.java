package com.blink.core.exception;

public class BlinkRuntimeException extends RuntimeException {
    public BlinkRuntimeException() {
    }

    public BlinkRuntimeException(String message) {
        super(message);
    }

    public BlinkRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BlinkRuntimeException(Throwable cause) {
        super(cause);
    }

    public BlinkRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
