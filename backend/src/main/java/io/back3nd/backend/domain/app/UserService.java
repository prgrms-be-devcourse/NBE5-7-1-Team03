package io.back3nd.backend.domain.app;

import io.back3nd.backend.domain.dao.UsersRepository;
import io.back3nd.backend.domain.dto.SignUpRequest;
import io.back3nd.backend.domain.dto.UserResponse;
import io.back3nd.backend.domain.entity.Users;
import io.back3nd.backend.global.exception.CustomException;
import io.back3nd.backend.global.exception.InvalidSignUpException;
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

    public void signUp(SignUpRequest request){
        validate(request);

        Users newUser = Users.builder()
                .email(request.getEmail())
                .nickname(request.getNickname())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        usersRepository.save(newUser);
    }

    public UserResponse findUser(String email) {
        Users findUser = usersRepository.findByEmail(email).orElseThrow(
                () -> new CustomException("일치하는 사용자 정보가 없습니다.")
        );

        return UserResponse.builder()
                .email(findUser.getEmail())
                .createdAt(findUser.getCreatedAt())
                .orders(findUser.getOrders())
                .build();
    }

    private void validate(SignUpRequest request){
        if(usersRepository.existsByEmail(request.getEmail())){
            throw new InvalidSignUpException("이미 사용 중인 이메일 입니다.");
        }
        if(usersRepository.existsByNickname(request.getNickname())){
            throw new InvalidSignUpException("이미 사용 중인 닉네임 입니다.");
        }
    }
}
