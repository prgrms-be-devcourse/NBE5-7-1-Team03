package io.back3nd.backend.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignUpRequest {

    private String nickname;

    private String email;

    private String password;
}
