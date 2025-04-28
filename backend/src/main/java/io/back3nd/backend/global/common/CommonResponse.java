package io.back3nd.backend.global.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse<T> {
    private final String message;
    private final T response;

    @Builder
    public CommonResponse(String message, T response) {
        this.message = message;
        this.response = response;
    }

    public static <T> CommonResponse<T> from(String message, T response) {
        return new CommonResponse<T>(message, response);
    }

    //response 없이 메시지만 보내고 싶은 경우
    public static CommonResponse<Object> from(String message) {
        return CommonResponse.builder()
                .message(message)
                .build();
    }
}