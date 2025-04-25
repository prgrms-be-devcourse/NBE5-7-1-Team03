package io.back3nd.backend.domain.app;

import io.back3nd.backend.domain.dao.UsersRepository;
import io.back3nd.backend.domain.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users findUser = usersRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("회원 정보가 존재하지 않습니다.")
        );

        SimpleGrantedAuthority auth = new SimpleGrantedAuthority(findUser.getRole().name());
        return new User(findUser.getEmail(),findUser.getPassword(), List.of(auth));
    }
}
