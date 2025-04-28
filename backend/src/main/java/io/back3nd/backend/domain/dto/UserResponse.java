package io.back3nd.backend.domain.dto;

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

    private String nickname;
    private String email;
    private Role role;
    private LocalDateTime createdAt;
    private List<OrderUpdateResponse> orders = new ArrayList<>();

    @Builder
    public UserResponse(String nickname, String email, Role role, LocalDateTime createdAt, List<OrderUpdateResponse> orders) {
        this.nickname = nickname;
        this.email = email;
        this.role = role;
        this.createdAt = createdAt;
        this.orders = orders;
    }
}
