package io.back3nd.backend.domain.app;

import io.back3nd.backend.domain.dao.OrdersRepository;
import io.back3nd.backend.domain.dto.OrderRequest;
import io.back3nd.backend.domain.dto.OrderResponse;
import io.back3nd.backend.domain.entity.Orders;
import io.back3nd.backend.domain.entity.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

        //예외 처리 사항
        //이메일 주소 우편번호 필수, 이메일 형식, ','이 감지되면 다중 입력으로 간주 후 오류 출력
        //나중에 추가 할 사항 : 존재하지 않는 유저 -> 회원 가입으로 이동?
    }

    public OrderResponse getOrder(Long id) {
        Orders orders = ordersRepository.findById(id).
                orElseThrow(() -> new NoSuchElementException("존재하지 않는 주문입니다."));
        return new OrderResponse(orders);
        //예외 처리 사항
        //존재하지 않는 주문 아이디, 이미 삭제된 주문
        //나중에 추가 할 사항 : 회원 관리 기능 추가 후 유저 아이디로 한 유저의 전체 주문 리스트 조회
    }

    public void deleteOrder(Long id) { //주문 삭제 = 주문 취소로 구현함
        Orders orders = ordersRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 주문입니다."));

        orders.setStatus(Status.CANCELLED);
        orders.setDeletedAt(LocalDateTime.now());

        ordersRepository.save(orders);
        //예외 처리 사항
        //존재하지 않는 주문 아이디, 이미 삭제된 주문
    }

}
