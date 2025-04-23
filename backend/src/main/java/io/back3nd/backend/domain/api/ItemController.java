package io.back3nd.backend.domain.api;

import io.back3nd.backend.domain.app.ItemService;
import io.back3nd.backend.domain.dto.ItemListResponse;
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

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemResponse> getItem(@PathVariable Long itemId) {

        ItemResponse item = itemService.findItem(itemId);

        return ResponseEntity.ok(item);
    }

    @GetMapping
    public ResponseEntity<ItemListResponse> getItems() {

        List<ItemResponse> itemList = itemService.findAll();
        ItemListResponse items = ItemListResponse.builder()
                .items(itemList)
                .build();

        return ResponseEntity.ok(items);
    }

    @PutMapping("{itemId}")
    public ResponseEntity<ItemMessageResponse> updateItem(@PathVariable Long itemId, @RequestBody ItemRequest itemRequest) {


        try {
            itemService.updateItem(itemId, itemRequest);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(ItemMessageResponse
                            .builder()
                            .message(e.getMessage())
                            .build()
                    );
        }

        ItemMessageResponse response = ItemMessageResponse.builder()
                .message("상품이 업데이트 되었습니다.")
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteItem(@PathVariable Long itemId) {
        return null;
    }
}

