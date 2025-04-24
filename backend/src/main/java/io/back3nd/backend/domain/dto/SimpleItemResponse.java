package io.back3nd.backend.domain.dto;

import io.back3nd.backend.domain.entity.Items;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SimpleItemResponse {

    private String name;

    private int price;

    private int stock;

    private String imageUrl;

    @Builder
    public SimpleItemResponse(String name, int price, int stock, String imageUrl) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
    }

    public static SimpleItemResponse from(Items item) {
        return SimpleItemResponse.builder()
                .name(item.getName())
                .price(item.getPrice())
                .stock(item.getStock())
                .imageUrl(item.getImageUrl())
                .build();
    }
}
