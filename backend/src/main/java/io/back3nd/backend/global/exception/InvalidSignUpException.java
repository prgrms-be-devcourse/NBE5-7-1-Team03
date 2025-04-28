package io.back3nd.backend.global.exception;

public class InvalidSignUpException extends RuntimeException{

    public InvalidSignUpException(String message) {
        super(message);
    }
}
