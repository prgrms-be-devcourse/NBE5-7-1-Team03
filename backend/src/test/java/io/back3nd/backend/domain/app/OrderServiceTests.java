package io.back3nd.backend.domain.app;

import io.back3nd.backend.domain.dao.OrdersRepository;
import io.back3nd.backend.domain.dao.UsersRepository;
import io.back3nd.backend.domain.dto.ItemRequest;
import io.back3nd.backend.domain.dto.OrderRequest;
import io.back3nd.backend.domain.dto.OrderResponse;
import io.back3nd.backend.domain.entity.Orders;
import io.back3nd.backend.domain.entity.Status;
import io.back3nd.backend.domain.entity.Users;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class OrderServiceTests {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private UsersRepository usersRepository;

    private Users testUser;
    @BeforeEach
    void setUp() {
        testUser = usersRepository.findByEmail("example@example.com")
                .orElseGet(() -> usersRepository.save(
                        Users.builder()
                                .email("example@example.com")
                                .password("1234")
                                .nickname("테스트유저")
                                .build()
                ));

        for (int i = 1; i <= 3; i++) {
            String name = "테스트커피" + i;
            ItemRequest itemRequest = new ItemRequest(name, 1000 * i, 100);
            try {
                itemService.addItem(itemRequest, null);
            } catch (Exception ignored) {
            }
        }
    }

    @Test
    @DisplayName("주문하기")
    void doOrdersTests() throws Exception {
        OrderRequest request = new OrderRequest(
                "example@example.com",
                "인천광역시 연수구",
                "12345",
                List.of(
                        new OrderRequest.OrderItemRequest(1L, 1),
                        new OrderRequest.OrderItemRequest(2L, 2),
                        new OrderRequest.OrderItemRequest(3L, 3)
                )
        );

        OrderResponse orderResponse = orderService.doOrder(request, testUser);

        assertThat(request).isNotNull();
        assertThat(request.getEmail()).isEqualTo("example@example.com");
        assertThat(request.getAddress()).isEqualTo("인천광역시 연수구");
        assertThat(request.getZipcode()).isEqualTo("12345");

        log.info("orderResponse.getId() = {}", orderResponse.getId());
        log.info("orderResponse.getStatus() = {}", orderResponse.getStatus());
    }

    @Test
    @DisplayName("주문 조회 테스트")
    void getOrdersTests() throws Exception {
        OrderRequest request = new OrderRequest(
                "exmple2@example.com",
                "이 편지는 영국에서부터 시작되어...",
                "67890",
                List.of(
                        new OrderRequest.OrderItemRequest(1L, 1),
                        new OrderRequest.OrderItemRequest(2L, 2),
                        new OrderRequest.OrderItemRequest(3L, 3)
                )
        );
        OrderResponse created = orderService.doOrder(request,testUser);

        OrderResponse getOrder = orderService.getOrder(created.getId());

        assertThat(getOrder).isNotNull();
        assertThat(getOrder.getId()).isEqualTo(created.getId());

        log.info("getOrder.getId() = {}", getOrder.getId());
        log.info("getOrder.getStatus() = {}", getOrder.getStatus());
    }

    @Test
    @DisplayName("주문 조회 실패 테스트")
    void getWrongOrderTest() throws Exception {
        Long invalidId = 0L;

        assertThatThrownBy(
                () -> {
                    orderService.getOrder(invalidId);
                }
        ).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("주문 삭제(취소) 테스트")
    void deleteOrdersTests() throws Exception {
        OrderRequest request = new OrderRequest(
                "exmple3@example.com",
                "취소할건데 주소 알아서 뭐하게요",
                "44444",
                List.of(
                        new OrderRequest.OrderItemRequest(1L, 1),
                        new OrderRequest.OrderItemRequest(2L, 2),
                        new OrderRequest.OrderItemRequest(3L, 3)
                )
        );
        OrderResponse created = orderService.doOrder(request, testUser);

        orderService.deleteOrder(created.getId());

        Orders cancelled = ordersRepository.findById(created.getId()).orElseThrow();

        assertThat(cancelled.getStatus()).isEqualTo(Status.CANCELLED);
    }
}