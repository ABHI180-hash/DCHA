package com.dcha.controller;

import com.dcha.dto.ApiResponse;
import com.dcha.dto.CollectionItemRequest;
import com.dcha.dto.CollectionRequest;
import com.dcha.entity.Collection;
import com.dcha.entity.CollectionItem;
import com.dcha.security.UserPrincipal;
import com.dcha.service.CollectionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collections")
public class CollectionController {

    private final CollectionService collectionService;

    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Collection>>> getUserCollections(@AuthenticationPrincipal UserPrincipal currentUser) {
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("User not authenticated"));
        }
        List<Collection> collections = collectionService.getUserCollections(currentUser.getId());
        return ResponseEntity.ok(ApiResponse.success(collections));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Collection>> getCollectionById(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable Long id) {
        Long userId = currentUser != null ? currentUser.getId() : null;
        Collection collection = collectionService.getCollectionById(userId, id);
        return ResponseEntity.ok(ApiResponse.success(collection));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Collection>> createCollection(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @Valid @RequestBody CollectionRequest request) {
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("User not authenticated"));
        }
        Collection collection = collectionService.createCollection(currentUser.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Collection created", collection));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Collection>> updateCollection(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable Long id,
            @RequestBody CollectionRequest request) {
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("User not authenticated"));
        }
        Collection collection = collectionService.updateCollection(currentUser.getId(), id, request);
        return ResponseEntity.ok(ApiResponse.success("Collection updated", collection));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCollection(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable Long id) {
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("User not authenticated"));
        }
        collectionService.deleteCollection(currentUser.getId(), id);
        return ResponseEntity.ok(ApiResponse.success("Collection deleted", null));
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<ApiResponse<List<CollectionItem>>> getCollectionItems(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable Long id) {
        Long userId = currentUser != null ? currentUser.getId() : null;
        List<CollectionItem> items = collectionService.getCollectionItems(userId, id);
        return ResponseEntity.ok(ApiResponse.success(items));
    }

    @PostMapping("/{id}/items")
    public ResponseEntity<ApiResponse<CollectionItem>> addItemToCollection(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable Long id,
            @Valid @RequestBody CollectionItemRequest request) {
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("User not authenticated"));
        }
        CollectionItem item = collectionService.addItemToCollection(currentUser.getId(), id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Item added to collection", item));
    }

    @DeleteMapping("/{id}/items/{itemId}")
    public ResponseEntity<ApiResponse<Void>> removeItemFromCollection(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable Long id,
            @PathVariable Long itemId) {
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("User not authenticated"));
        }
        collectionService.removeItemFromCollection(currentUser.getId(), id, itemId);
        return ResponseEntity.ok(ApiResponse.success("Item removed from collection", null));
    }
}
