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

    private String imageUrl;

    @Builder
    public ItemResponse(Long id, String name, int price, int stock, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
    }

    public static ItemResponse from(Items item) {
        return ItemResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .stock(item.getStock())
                .imageUrl(item.getImageUrl())
                .build();
    }
}
