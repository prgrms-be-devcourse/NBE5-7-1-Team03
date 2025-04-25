package io.back3nd.backend.domain.dao;

import io.back3nd.backend.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
}
