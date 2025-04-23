package io.back3nd.backend.domain.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderRequest {

    private String email;
    private String address;
    private String zipcode;
    private List<Long> itemId;


    public OrderRequest(String email, String address, String zipcode, List<Long> itemId) {
        this.email = email;
        this.address = address;
        this.zipcode = zipcode;
        this.itemId = itemId;
    }
}
