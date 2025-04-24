package io.back3nd.backend.domain.api;

import io.back3nd.backend.domain.app.OrderService;
import io.back3nd.backend.domain.dto.OrderRequest;
import io.back3nd.backend.domain.dto.OrderResponse;
import io.back3nd.backend.global.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static io.back3nd.backend.global.common.StatusCode.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<CommonResponse<OrderResponse>> doOrder(
            @RequestBody OrderRequest orderRequest) {
        return ResponseEntity.status(ORDER_SUCCESS.getStatus())
                .body(CommonResponse.from(ORDER_SUCCESS.getMessage(), orderService.doOrder(orderRequest)));
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<CommonResponse<OrderResponse>> getOrder(
            @PathVariable Long id) {
        return ResponseEntity.ok(CommonResponse.from(ORDER_FOUND.getMessage(), orderService.getOrder(id)));
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<CommonResponse<Object>> deleteOrder(
            @PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok(CommonResponse.from(ORDER_DELETE.getMessage(), orderService.getOrder(id)));
    }
}

