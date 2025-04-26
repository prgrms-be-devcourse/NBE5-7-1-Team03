package io.back3nd.backend.domain.dto;

import io.back3nd.backend.domain.entity.Orders;
import io.back3nd.backend.domain.entity.Status;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderUpdateResponse {

    private Long id;
    private String address;
    private String zipcode;
    private Status status;
    private List<OrderItemResponse> items;

    public OrderUpdateResponse(Orders orders) {
        this.id = orders.getId();
        this.address = orders.getAddress();
        this.zipcode = orders.getZipcode();
        this.status = orders.getStatus();
        this.items = orders.getOrderItems().stream()
                .map(item -> OrderItemResponse.builder()
                        .id(item.getId())
                        .quantity(item.getQuantity())
                        .name(item.getItem().getName())
                        .build())
                .toList();
    }
}
