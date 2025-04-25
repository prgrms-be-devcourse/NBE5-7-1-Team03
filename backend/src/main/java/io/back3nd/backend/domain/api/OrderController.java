package io.back3nd.backend.domain.api;

import io.back3nd.backend.domain.app.OrderService;
import io.back3nd.backend.domain.dao.UsersRepository;
import io.back3nd.backend.domain.dto.OrderRequest;
import io.back3nd.backend.domain.dto.OrderResponse;
import io.back3nd.backend.domain.dto.UserDetailsImpl;
import io.back3nd.backend.domain.entity.Users;
import io.back3nd.backend.global.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static io.back3nd.backend.global.common.StatusCode.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    private final OrderService orderService;
    private final UsersRepository usersRepository;

    @PostMapping("/orders")
    public ResponseEntity<CommonResponse<OrderResponse>> doOrder(
            @RequestBody OrderRequest orderRequest,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        if (userDetails == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        Users user = userDetails.getUser();

        OrderResponse orderResponse = orderService.doOrder(orderRequest, user);
        return ResponseEntity.status(ORDER_SUCCESS.getStatus())
                .body(CommonResponse.from(ORDER_SUCCESS.getMessage(), orderResponse));
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<CommonResponse<OrderResponse>> getOrder(
            @PathVariable Long id) {
        return ResponseEntity.status(ORDER_FOUND.getStatus())
                .body(CommonResponse.from(ORDER_FOUND.getMessage(), orderService.getOrder(id)));
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<CommonResponse<Object>> deleteOrder(
            @PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.status(ORDER_DELETE.getStatus())
                .body(CommonResponse.from(ORDER_DELETE.getMessage()));
    }
}

