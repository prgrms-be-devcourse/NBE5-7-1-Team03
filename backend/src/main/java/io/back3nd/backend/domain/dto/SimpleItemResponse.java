package io.back3nd.backend.domain.dto;

import io.back3nd.backend.domain.entity.Items;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SimpleItemResponse {

    private String name;

    private int price;

    private int stock;

    private String storeFileName;

    @Builder
    public SimpleItemResponse(String name, int price, int stock, String storeFileName) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.storeFileName = storeFileName;
    }

    public static SimpleItemResponse from(Items item) {
        return SimpleItemResponse.builder()
                .name(item.getName())
                .price(item.getPrice())
                .stock(item.getStock())
                .storeFileName(item.getImageFile().getStoreFileName())
                .build();
    }
}
