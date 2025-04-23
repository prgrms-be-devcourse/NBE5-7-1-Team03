package io.back3nd.backend.domain.entity;

import io.back3nd.backend.domain.dto.ItemRequest;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Items {

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int price;

    private int stock;

    private String imageUrl;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    @Builder
    public Items(String name, int price, int stock, String imageUrl) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
    }

    public void updateItem(ItemRequest itemRequest) {
        this.name = itemRequest.getName();
        this.price = itemRequest.getPrice();
        this.stock = itemRequest.getStock();
        this.imageUrl = itemRequest.getImageUrl();
        this.updatedAt = LocalDateTime.now();
    }
}
