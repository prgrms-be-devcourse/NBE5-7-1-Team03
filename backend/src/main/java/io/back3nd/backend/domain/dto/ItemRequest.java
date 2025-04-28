package io.back3nd.backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemRequest {

    private String name;

    private int price;

    private int stock;

}
