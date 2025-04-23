package io.back3nd.backend.domain.api;

import io.back3nd.backend.domain.app.ItemService;
import io.back3nd.backend.domain.dto.ItemMessageResponse;
import io.back3nd.backend.domain.dto.ItemRequest;
import io.back3nd.backend.domain.dto.ItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemMessageResponse> createItem(@RequestBody ItemRequest itemRequest) {

        try {
            itemService.addItem(itemRequest);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(ItemMessageResponse
                            .builder()
                            .message(e.getMessage())
                            .build()
                    );
        }

        ItemMessageResponse response = ItemMessageResponse.builder()
                .message("상품이 등록 되었습니다.")
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ItemResponse>> getItems() {
        return null;
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteItem(@PathVariable Long itemId) {
        return null;
    }
}

