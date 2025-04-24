package io.back3nd.backend.domain.app;

import io.back3nd.backend.domain.dao.OrdersRepository;
import io.back3nd.backend.domain.dto.OrderRequest;
import io.back3nd.backend.domain.dto.OrderResponse;
import io.back3nd.backend.domain.entity.Orders;
import io.back3nd.backend.domain.entity.Status;
import io.back3nd.backend.global.exception.InvalidOrderException;
import io.back3nd.backend.global.exception.InvalidOrderStateException;
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

        String email = orderRequest.getEmail();
        String address = orderRequest.getAddress();
        String zipcode = orderRequest.getZipcode();

        checkMultipleOrders(email, address, zipcode);
        checkEssentialInput(email, address, zipcode);
        checkInvalidEmail(email);

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

        checkOrderState(orders);

        return new OrderResponse(orders);
    }

    public void deleteOrder(Long id) { //주문 삭제 = 주문 취소로 구현함
        Orders orders = ordersRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 주문입니다."));

        checkOrderState(orders);

        orders.setStatus(Status.CANCELLED);
        orders.setDeletedAt(LocalDateTime.now());

        ordersRepository.save(orders);
    }

    private static void checkEssentialInput(String email, String address, String zipcode) {
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidOrderException("이메일은 필수입니다.");
        }

        if (address == null || address.trim().isEmpty()) {
            throw new InvalidOrderException("주소는 필수입니다.");
        }

        if (zipcode == null || zipcode.trim().isEmpty()) {
            throw new InvalidOrderException("우편번호는 필수입니다.");
        }
    }

    private static void checkInvalidEmail(String email) {
        if (!email.matches("^[\\w\\.-]+@[\\w\\.-]+\\.[a-zA-Z]{2,}$")) {
            throw new InvalidOrderException("이메일 형식이 올바르지 않습니다.");
        }
    }

    private static void checkMultipleOrders(String email, String address, String zipcode) {
        if (email.contains(",") || address.contains(",") || zipcode.contains(",")) {
            throw new InvalidOrderException("다중 주문은 진행할 수 없습니다.");
        }
    }

    private static void checkOrderState(Orders orders) {
        if (orders.getStatus() == Status.CANCELLED) {
            throw new InvalidOrderStateException("이미 취소된 주문입니다.");
        }
    }

}
