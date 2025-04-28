package io.back3nd.backend.domain.dto;

import lombok.Getter;

@Getter
public class OrderUpdateRequest {

    private String address;
    private String zipCode;

    public OrderUpdateRequest(String address, String zipCode) {
        this.address = address;
        this.zipCode = zipCode;
    }
}
