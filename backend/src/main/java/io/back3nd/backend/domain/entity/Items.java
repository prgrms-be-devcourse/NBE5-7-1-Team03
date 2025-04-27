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

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="image_file_id")
    private ImageFiles imageFile;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    @Builder
    public Items(String name, int price, int stock, ImageFiles imageFile) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.imageFile=imageFile;
    }

    public void updateItem(ItemRequest itemRequest, ImageFiles imageFile) {
        this.name = itemRequest.getName();
        this.price = itemRequest.getPrice();
        this.stock = itemRequest.getStock();
        this.imageFile=imageFile;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateItemWithoutImage(ItemRequest itemRequest) {
        this.name = itemRequest.getName();
        this.price = itemRequest.getPrice();
        this.stock = itemRequest.getStock();
        this.updatedAt = LocalDateTime.now();
    }

    public void decreaseStock(int quantity){
        if(this.stock < quantity ){
            throw new IllegalArgumentException("재고가 부족합니다.");
        }
        this.stock -= quantity;
    }

    public void increaseStock(int quantity){
        this.stock += quantity;
    }

}
