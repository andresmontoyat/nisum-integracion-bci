package com.nisum.integration.bci.domain.exception;

public class TokenException extends RuntimeException{
    public TokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
