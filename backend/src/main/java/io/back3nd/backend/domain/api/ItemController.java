package io.back3nd.backend.domain.api;

import io.back3nd.backend.domain.app.ImageFileService;
import io.back3nd.backend.domain.app.ItemService;
import io.back3nd.backend.domain.dto.ItemRequest;
import io.back3nd.backend.domain.dto.ItemResponse;
import io.back3nd.backend.domain.dto.SimpleItemResponse;
import io.back3nd.backend.domain.entity.ImageFiles;
import io.back3nd.backend.global.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import static io.back3nd.backend.global.common.StatusCode.*;

@RestController
@RequestMapping("/items")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ImageFileService imageFileService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse<Object>> createItem(@RequestPart("item") ItemRequest itemRequest,
                                                             @RequestPart("image") MultipartFile image) throws IOException {

        ImageFiles imageFile = imageFileService.storeFile(image);
        itemService.addItem(itemRequest, imageFile);

        return ResponseEntity.status(ITEM_CREATED.getStatus())
                .body(CommonResponse.from(ITEM_CREATED.getMessage()));
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<CommonResponse<SimpleItemResponse>> getItem(@PathVariable Long itemId) {

        SimpleItemResponse item = itemService.findItem(itemId);

        return ResponseEntity.status(ITEM_FOUND.getStatus())
                .body(CommonResponse.from(ITEM_FOUND.getMessage(), item));
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<ItemResponse>>> getItems() {

        List<ItemResponse> itemList = itemService.findAll();

        return ResponseEntity.status(ITEM_FOUND.getStatus())
                .body(CommonResponse.from(ITEM_FOUND.getMessage(),itemList));
    }

    @PutMapping(path="{itemId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse<Object>> updateItem(@PathVariable Long itemId,
                                                             @RequestPart("item") ItemRequest itemRequest,
                                                             @RequestPart("image") MultipartFile image) throws IOException {

        ImageFiles imageFile = imageFileService.storeFile(image);
        itemService.updateItem(itemId, itemRequest, imageFile);

        return ResponseEntity.status(ITEM_UPDATED.getStatus())
                .body(CommonResponse.from(ITEM_UPDATED.getMessage()));
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<CommonResponse<Object>> deleteItem(@PathVariable Long itemId) {

         itemService.deleteItem(itemId);

        return ResponseEntity.status(ITEM_DELETE.getStatus())
                .body(CommonResponse.from(ITEM_DELETE.getMessage()));
    }

    @GetMapping("/images/{filename}")
    public Resource showImage(@PathVariable("filename") String filename) throws MalformedURLException {
        return new UrlResource("file:"+imageFileService.getFullPath(filename));
    }
}

