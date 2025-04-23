package io.back3nd.backend.domain.app;

import io.back3nd.backend.domain.dao.OrdersRepository;
import io.back3nd.backend.domain.dto.OrderRequest;
import io.back3nd.backend.domain.dto.OrderResponse;
import io.back3nd.backend.domain.entity.Orders;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
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


}
