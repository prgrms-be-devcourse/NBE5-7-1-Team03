package io.back3nd.backend.domain.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderRequest {

    private String email;
    private String address;
    private String zipcode;
    private List<OrderItemRequest> orderItems;

    public OrderRequest(String email, String address, String zipcode, List<OrderItemRequest> orderItems) {
        this.email = email;
        this.address = address;
        this.zipcode = zipcode;
        this.orderItems = orderItems;
    }

    @Getter
    public static class OrderItemRequest {
        private Long itemId;
        private int quantity;

        public OrderItemRequest(Long itemId, int quantity) {
            this.itemId = itemId;
            this.quantity = quantity;
        }
    }
}
