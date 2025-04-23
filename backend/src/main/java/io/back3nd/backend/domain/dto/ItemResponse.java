package io.back3nd.backend.domain.dto;

import lombok.Getter;

@Getter
public class ItemResponse {

    private String name;

    private int price;

    private int stock;

    private String imageUrl;
}
