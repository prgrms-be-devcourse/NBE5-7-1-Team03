package io.back3nd.backend.domain.app;

import io.back3nd.backend.domain.dao.OrdersRepository;
import io.back3nd.backend.domain.dto.OrderRequest;
import io.back3nd.backend.domain.dto.OrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class OrderServiceTests {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrdersRepository ordersRepository;

    @Test
    @DisplayName("주문하기")
    void doOrdersTests() throws Exception {
        OrderRequest request = new OrderRequest(
                "example@example.com",
                "인천광역시 연수구",
                "12345",
                List.of(1L,2L,3L)
        );

        OrderResponse orderResponse = orderService.doOrder(request);

        assertThat(request).isNotNull();
        assertThat(request.getEmail()).isEqualTo("example@example.com");
        assertThat(request.getAddress()).isEqualTo("인천광역시 연수구");
        assertThat(request.getZipcode()).isEqualTo("12345");

        log.info("orderResponse.getId() = {}", orderResponse.getId());
        log.info("orderResponse.getStatus() = {}", orderResponse.getStatus());

    }

}