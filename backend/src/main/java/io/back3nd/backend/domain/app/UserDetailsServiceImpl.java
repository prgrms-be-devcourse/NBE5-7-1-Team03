package io.back3nd.backend.domain.app;

import io.back3nd.backend.domain.dao.UsersRepository;
import io.back3nd.backend.domain.dto.UserDetailsImpl;
import io.back3nd.backend.domain.entity.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users findUser = usersRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("회원 정보가 존재하지 않습니다.")
        );

        return new UserDetailsImpl(findUser);
    }
}
