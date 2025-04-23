package io.back3nd.backend.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String email;

    private String address;

    private String zipcode;

    @Enumerated(EnumType.STRING)
    private Status status = Status.RECEIVED;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now(); //아직 구현하지는 않을 예정

    @Builder
    public Orders(String email, String address, String zipcode) {
        this.email = email;
        this.address = address;
        this.zipcode = zipcode;
    }
}
