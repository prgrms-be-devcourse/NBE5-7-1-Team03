package io.back3nd.backend.domain.dto;

import io.back3nd.backend.domain.entity.Users;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class UserDetailsImpl extends User {

    private Users user;

    public UserDetailsImpl(Users user) {
        super(user.getEmail(), user.getPassword(), List.of(new SimpleGrantedAuthority(user.getRole().name())));
        this.user = user;
    }
}
