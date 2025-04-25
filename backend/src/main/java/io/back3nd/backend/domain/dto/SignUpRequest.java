package io.back3nd.backend.domain.dto;

import lombok.Data;

//todo validation 추가
@Data
public class SignUpRequest {

    private String nickname;

    private String email;

    private String password;
}
