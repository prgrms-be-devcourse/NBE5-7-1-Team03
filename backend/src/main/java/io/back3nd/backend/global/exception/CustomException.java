package io.back3nd.backend.global.exception;

public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}
