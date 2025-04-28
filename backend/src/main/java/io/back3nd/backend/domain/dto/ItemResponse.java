package io.back3nd.backend.domain.dto;

import io.back3nd.backend.domain.entity.Items;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ItemResponse {

    private Long id;

    private String name;

    private int price;

    private int stock;

    private String storeFileName;

    @Builder
    public ItemResponse(Long id, String name, int price, int stock, String storeFileName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.storeFileName = storeFileName;
    }

    public static ItemResponse from(Items item) {
        return ItemResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .stock(item.getStock())
                .storeFileName(item.getImageFile().getStoreFileName())
                .build();
    }
}
