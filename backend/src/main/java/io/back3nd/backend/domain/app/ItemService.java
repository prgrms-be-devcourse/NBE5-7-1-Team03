package io.back3nd.backend.domain.app;

import io.back3nd.backend.domain.dao.ItemsRepository;
import io.back3nd.backend.domain.dto.ItemRequest;
import io.back3nd.backend.domain.entity.Items;
import io.back3nd.backend.global.exception.DuplicatedNameException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemsRepository itemsRepository;

    public void addItem(ItemRequest itemRequest) {
        if (itemsRepository.findByName(itemRequest.getName()).isPresent()) {
            throw new DuplicatedNameException("해당 상품명이 이미 존재합니다.");
        }

        Items items = Items.builder()
                .name(itemRequest.getName())
                .price(itemRequest.getPrice())
                .stock(itemRequest.getStock())
                .imageUrl(itemRequest.getImageUrl())
                .build();

        itemsRepository.save(items);
    }
}
