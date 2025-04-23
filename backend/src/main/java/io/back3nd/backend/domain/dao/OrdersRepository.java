package io.back3nd.backend.domain.dao;

import io.back3nd.backend.domain.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
