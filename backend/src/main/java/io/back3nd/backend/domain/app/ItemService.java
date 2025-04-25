package io.back3nd.backend.domain.app;

import io.back3nd.backend.domain.dao.ItemsRepository;
import io.back3nd.backend.domain.dto.ItemRequest;
import io.back3nd.backend.domain.dto.ItemResponse;
import io.back3nd.backend.domain.dto.SimpleItemResponse;
import io.back3nd.backend.domain.entity.ImageFiles;
import io.back3nd.backend.domain.entity.Items;
import io.back3nd.backend.global.exception.DuplicatedNameException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemsRepository itemsRepository;

    public void addItem(ItemRequest itemRequest, ImageFiles imageFile) {
        if (itemsRepository.findByName(itemRequest.getName()).isPresent()) {
            throw new DuplicatedNameException("해당 상품명이 이미 존재합니다.");
        }

        Items item = Items.builder()
                .name(itemRequest.getName())
                .price(itemRequest.getPrice())
                .stock(itemRequest.getStock())
                .imageFile(imageFile)
                .build();

        itemsRepository.save(item);
    }

    public List<ItemResponse> findAll() {
        return itemsRepository.findAll()
                .stream()
                .map(i -> ItemResponse.from(i))
                .toList();
    }

    public SimpleItemResponse findItem(Long itemId) {
        Items item = itemsRepository.findById(itemId)
                .orElseThrow(
                        () -> new NoSuchElementException("해당하는 상품을 찾을 수 없습니다.")
                );

        return SimpleItemResponse.from(item);
    }

    public void updateItem(Long itemId, ItemRequest itemRequest, ImageFiles imageFile) {
        Items findItem = itemsRepository.findById(itemId)
                .orElseThrow(
                        () -> new NoSuchElementException("해당하는 상품을 찾을 수 없습니다.")
                );

        if (!itemRequest.getName().equals(findItem.getName())
                && itemsRepository.findByName(itemRequest.getName()).isPresent()) {
            throw new DuplicatedNameException("이미 존재하는 상품명입니다.");
        }

        findItem.updateItem(itemRequest, imageFile);
    }

    public void deleteItem(Long itemId) {
        Items findItem = itemsRepository.findById(itemId)
                .orElseThrow(() -> new NoSuchElementException("해당하는 상품이 존재하지 않습니다."));

        itemsRepository.delete(findItem);
    }
}
