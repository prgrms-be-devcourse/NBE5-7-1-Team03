package io.back3nd.backend.domain.app;

import io.back3nd.backend.domain.dao.UsersRepository;
import io.back3nd.backend.domain.dto.SignUpRequest;
import io.back3nd.backend.domain.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public Users signUp(SignUpRequest request){
        Users newUser = Users.builder()
                .email(request.getEmail())
                .nickname(request.getNickname())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        return usersRepository.save(newUser);
    }
}
