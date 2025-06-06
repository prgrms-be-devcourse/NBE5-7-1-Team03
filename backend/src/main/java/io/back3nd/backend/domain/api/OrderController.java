package io.back3nd.backend.domain.api;

import io.back3nd.backend.domain.app.OrderService;
import io.back3nd.backend.domain.dto.OrderRequest;
import io.back3nd.backend.domain.dto.OrderResponse;
import io.back3nd.backend.domain.dto.OrderUpdateRequest;
import io.back3nd.backend.domain.dto.UserDetailsImpl;
import io.back3nd.backend.domain.entity.Users;
import io.back3nd.backend.global.common.CommonResponse;
import io.back3nd.backend.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static io.back3nd.backend.global.common.StatusCode.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<CommonResponse<OrderResponse>> doOrder(
            @RequestBody OrderRequest orderRequest,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        if (userDetails == null) {
            throw new CustomException("로그인을 해주세요");
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

    @PutMapping("/orders/{id}")
    public ResponseEntity<CommonResponse<Object>> updateOrder(
            @PathVariable Long id, @RequestBody OrderUpdateRequest orderUpdateRequest) {
        orderService.updateOrder(id, orderUpdateRequest);
        return ResponseEntity.status(ORDER_UPDATED.getStatus())
                .body(CommonResponse.from(ORDER_UPDATED.getMessage()));
    }

}

