package io.back3nd.backend.domain.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

// 사용 중지
@Getter
public class ItemListResponse {

    private List<ItemResponse> items;

    @Builder
    public ItemListResponse(List<ItemResponse> items) {
        this.items = items;
    }
}
