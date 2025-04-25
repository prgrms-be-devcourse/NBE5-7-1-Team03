package io.back3nd.backend.domain.dto;

import io.back3nd.backend.domain.entity.Orders;
import io.back3nd.backend.domain.entity.Role;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UserResponse {

    private String email;
    private Role role;
    private LocalDateTime createdAt;
    private List<Orders> orders = new ArrayList<>();

    @Builder
    public UserResponse(String email, Role role, LocalDateTime createdAt, List<Orders> orders) {
        this.email = email;
        this.role = role;
        this.createdAt = createdAt;
        this.orders = orders;
    }
}
