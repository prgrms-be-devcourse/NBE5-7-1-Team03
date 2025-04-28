package io.back3nd.backend.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "order_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItems {

    @Id
    @Column(name = "order_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Items item;

    @Setter
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;

    private int quantity;

    @Builder
    public OrderItems(Items item, Orders order, int quantity) {
        this.item = item;
        this.order = order;
        this.quantity = quantity;
    }
}

