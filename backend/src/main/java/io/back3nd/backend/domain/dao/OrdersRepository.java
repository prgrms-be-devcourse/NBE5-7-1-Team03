package io.back3nd.backend.domain.dao;

import io.back3nd.backend.domain.entity.Orders;
import io.back3nd.backend.domain.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByStatusAndCreatedAtBetween(Status status, LocalDateTime start, LocalDateTime end);
}
