package io.back3nd.backend.domain.app;

import io.back3nd.backend.domain.dao.OrdersRepository;
import io.back3nd.backend.domain.dto.OrderRequest;
import io.back3nd.backend.domain.dto.OrderResponse;
import io.back3nd.backend.domain.entity.Orders;
import io.back3nd.backend.domain.entity.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrdersRepository ordersRepository;

    public OrderResponse doOrder(OrderRequest orderRequest) {
        Orders orders = Orders.builder()
                .email(orderRequest.getEmail())
                .address(orderRequest.getAddress())
                .zipcode(orderRequest.getZipcode())
                .build();

        Orders saved = ordersRepository.save(orders);
        return new OrderResponse(saved);
    }

    public OrderResponse getOrder(Long id) {
        Orders orders = ordersRepository.findById(id).
                orElseThrow(() -> new NoSuchElementException("존재하지 않는 주문입니다."));
        return new OrderResponse(orders);
    }

    public void deleteOrder(Long id) { //주문 삭제 = 주문 취소로 구현함
        Orders orders = ordersRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 주문입니다."));

        orders.setStatus(Status.CANCELLED);
        orders.setDeletedAt(LocalDateTime.now());

        ordersRepository.save(orders);
    }

}
