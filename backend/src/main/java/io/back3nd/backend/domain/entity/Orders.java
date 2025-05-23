package io.back3nd.backend.domain.entity;

import io.back3nd.backend.domain.dto.OrderUpdateRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    private String email;

    @NonNull
    private String address;

    @NonNull
    private String zipcode;

    @Enumerated(EnumType.STRING)
    private Status status = Status.RECEIVED;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItems> orderItems = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @Builder
    public Orders(String email, String address, String zipcode) {
        this.email = email;
        this.address = address;
        this.zipcode = zipcode;
    }

    public void updateInfo(OrderUpdateRequest orderUpdateRequest) {
        this.address = orderUpdateRequest.getAddress();
        this.zipcode = orderUpdateRequest.getZipCode();
        this.updatedAt = LocalDateTime.now();
    }

}
