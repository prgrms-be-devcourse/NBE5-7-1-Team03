package io.back3nd.backend.global.exception;

import io.back3nd.backend.global.common.StatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomExceptionStatus extends RuntimeException {
    private final StatusCode statusCode;
}
