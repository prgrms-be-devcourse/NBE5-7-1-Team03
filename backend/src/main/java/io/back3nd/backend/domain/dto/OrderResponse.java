package io.back3nd.backend.domain.dto;

import io.back3nd.backend.domain.entity.Orders;
import io.back3nd.backend.domain.entity.Status;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderResponse {

    private Long id;
    private String email;
    private String address;
    private String zipcode;
    private Status status;
    private LocalDateTime createdAt;

    public OrderResponse(Orders order) {
        this.id = order.getId();
        this.email = order.getEmail();
        this.address = order.getAddress();
        this.zipcode = order.getZipcode();
        this.status = order.getStatus();
        this.createdAt = order.getCreatedAt();
    }
}