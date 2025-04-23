package io.back3nd.backend.global.exception;

public class DuplicatedNameException extends RuntimeException {

    public DuplicatedNameException() {
    }

    public DuplicatedNameException(String message) {
        super(message);
    }

    public DuplicatedNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedNameException(Throwable cause) {
        super(cause);
    }
}
