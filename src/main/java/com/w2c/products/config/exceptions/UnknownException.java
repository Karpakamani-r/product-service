package com.w2c.products.config.exceptions;

public class UnknownException extends RuntimeException {
    public UnknownException(String message, Throwable cause) {
        super(message, cause);
    }
}
