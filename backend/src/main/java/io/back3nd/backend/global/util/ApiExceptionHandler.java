package io.back3nd.backend.global.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    // NoSuchElementException
    @ExceptionHandler
    public ResponseEntity<?> handleRuntimeException(RuntimeException e) {

        log.info("ExceptionHandler = {}", e.getMessage());
        ApiErrorResponse response = ApiErrorResponse.builder()
                .message(e.getMessage())
                .build();

        return ResponseEntity.badRequest().body(response);
    }
}
