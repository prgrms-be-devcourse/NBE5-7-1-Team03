package io.back3nd.backend.domain.dao;

import io.back3nd.backend.domain.entity.Role;
import io.back3nd.backend.domain.entity.Users;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminUserInit {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init(){

        Users admin = Users.builder()
                .email("jeeun03@gmail.com")
                .nickname("어드민인데용")
                .password(passwordEncoder.encode("1234"))
                .build();

        admin.setRole(Role.ADMIN);

        usersRepository.save(admin);
    }
}
