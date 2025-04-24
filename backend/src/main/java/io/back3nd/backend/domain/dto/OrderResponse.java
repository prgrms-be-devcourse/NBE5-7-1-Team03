package io.back3nd.backend.domain.dto;

import io.back3nd.backend.domain.entity.Orders;
import io.back3nd.backend.domain.entity.Status;
import lombok.Getter;

@Getter
public class OrderResponse {

    private Long id;
    private Status status;

    public OrderResponse(Orders order) {
        this.id = order.getId();
        this.status = order.getStatus();
    }
}