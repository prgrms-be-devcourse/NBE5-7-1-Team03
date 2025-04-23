package io.back3nd.backend.global.util;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiErrorResponse {
    private String message;
}
