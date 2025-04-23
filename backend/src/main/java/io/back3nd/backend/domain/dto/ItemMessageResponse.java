package io.back3nd.backend.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemMessageResponse {
    private String message;
}
