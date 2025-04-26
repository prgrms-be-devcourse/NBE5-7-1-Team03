package io.back3nd.backend.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderItemResponse {

    private Long id;
    private String name;
    private int quantity;

    @Builder
    public OrderItemResponse(Long id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }
}
