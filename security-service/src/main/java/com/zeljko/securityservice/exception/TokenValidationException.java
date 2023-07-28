package com.zeljko.securityservice.exception;

public class TokenValidationException extends Throwable {
    public TokenValidationException(String message) {
        super(message);
    }
}
