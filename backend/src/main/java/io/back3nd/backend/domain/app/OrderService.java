package io.back3nd.backend.domain.app;

import io.back3nd.backend.domain.dao.ItemsRepository;
import io.back3nd.backend.domain.dao.OrdersRepository;
import io.back3nd.backend.domain.dto.OrderRequest;
import io.back3nd.backend.domain.dto.OrderResponse;
import io.back3nd.backend.domain.dto.OrderUpdateRequest;
import io.back3nd.backend.domain.entity.*;
import io.back3nd.backend.global.exception.InvalidOrderException;
import io.back3nd.backend.global.exception.InvalidOrderStateException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final ItemsRepository itemsRepository;
    private final OrdersRepository ordersRepository;

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

    private static void checkUpdateAvailable(Orders orders) {
        if (orders.getStatus() == Status.COMPLETED) {
            throw new InvalidOrderStateException("이미 배송완료 된 주문입니다.");
        }
        if (orders.getStatus() == Status.CANCELLED) {
            throw new InvalidOrderStateException("취소 된 주문입니다.");
        }
    }

    private static void checkValidUpdateInfo(OrderUpdateRequest orderUpdateRequest) {
        if (orderUpdateRequest.getAddress() == null || orderUpdateRequest.getZipCode() == null) {
            throw new InvalidOrderException("필수 정보가 없습니다.");
        }
        if (orderUpdateRequest.getAddress().isBlank() || orderUpdateRequest.getZipCode().isBlank()) {
            throw new InvalidOrderException("필수 정보가 없습니다.");
        }
    }

    public OrderResponse doOrder(OrderRequest orderRequest, Users user) {
        if (orderRequest.getOrderItems().isEmpty()) {
            throw new InvalidOrderException("주문할 상품을 선택해주세요.");
        }

        checkEssentialInput(orderRequest.getEmail(), orderRequest.getAddress(), orderRequest.getZipcode());

        List<OrderItems> orderItems = orderRequest.getOrderItems().stream().map(req -> {
            Items item = itemsRepository.findById(req.getItemId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이템 번호 입니다."));
            item.decreaseStock(req.getQuantity());

            return OrderItems.builder().item(item).quantity(req.getQuantity()).build();
        }).toList();

        Orders orders = Orders.builder()
                .email(orderRequest.getEmail())
                .address(orderRequest.getAddress())
                .zipcode(orderRequest.getZipcode())
                .build();

        orders.setUser(user);

        for (OrderItems orderItem : orderItems) {
            orderItem.setOrder(orders);
        }

        orders.setOrderItems(orderItems);

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

        //재고 원복
        for (OrderItems orderItem : orders.getOrderItems()) {
            orderItem.getItem().increaseStock(orderItem.getQuantity());
        }

        orders.setStatus(Status.CANCELLED);
        orders.setDeletedAt(LocalDateTime.now());

        ordersRepository.save(orders);
    }

    public void updateOrder(Long id, OrderUpdateRequest orderUpdateRequest) {
        Orders orders = ordersRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 주문입니다."));
        checkUpdateAvailable(orders);
        checkValidUpdateInfo(orderUpdateRequest);
        orders.updateInfo(orderUpdateRequest);
    }

//    @Scheduled(cron = "0 0 14 * * *", zone = "Asia/Seoul")
//    public void updateOrdersStatusToShipping() {
//        LocalDate yesterday = LocalDate.now().minusDays(1);
//
//        LocalDateTime startOfYesterday = yesterday.atStartOfDay();
//        LocalDateTime endOfYesterday = yesterday.atTime(23, 59, 59, 999_999_999);
//
//        List<Orders> ordersList = ordersRepository.findByStatusAndCreatedAtBetween(
//                Status.RECEIVED,
//                startOfYesterday,
//                endOfYesterday
//        );
//
//        for (Orders order : ordersList) {
//            order.setStatus(Status.SHIPPING);
//        }
//    }

    @Scheduled(fixedDelay = 30000)
    public void updateOrdersStatusToShipping() {
        List<Orders> ordersList = ordersRepository.findByStatus(Status.RECEIVED);

        for (Orders order : ordersList) {
            order.setStatus(Status.SHIPPING);
        }
    }
}
