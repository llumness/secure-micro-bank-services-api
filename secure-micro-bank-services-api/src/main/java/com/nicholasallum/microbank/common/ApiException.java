package com.nicholasallum.microbank.common;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
