package com.dcha.controller;

import com.dcha.dto.ApiResponse;
import com.dcha.dto.FavoriteRequest;
import com.dcha.entity.Favorite;
import com.dcha.security.UserPrincipal;
import com.dcha.service.FavoriteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Favorite>>> getUserFavorites(@AuthenticationPrincipal UserPrincipal currentUser) {
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("User not authenticated"));
        }
        List<Favorite> favorites = favoriteService.getUserFavorites(currentUser.getId());
        return ResponseEntity.ok(ApiResponse.success(favorites));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Favorite>> addFavorite(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @Valid @RequestBody FavoriteRequest request) {
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("User not authenticated"));
        }
        Favorite favorite = favoriteService.addFavorite(currentUser.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Added to favorites", favorite));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> removeFavorite(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable Long id) {
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("User not authenticated"));
        }
        favoriteService.removeFavorite(currentUser.getId(), id);
        return ResponseEntity.ok(ApiResponse.success("Favorite removed successfully", null));
    }

    @DeleteMapping("/item")
    public ResponseEntity<ApiResponse<Void>> removeFavoriteByItem(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestParam String itemType,
            @RequestParam Long itemId) {
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("User not authenticated"));
        }
        favoriteService.removeFavoriteByItem(currentUser.getId(), itemType, itemId);
        return ResponseEntity.ok(ApiResponse.success("Favorite removed successfully", null));
    }

    @GetMapping("/check")
    public ResponseEntity<ApiResponse<Map<String, Boolean>>> checkIsFavorited(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestParam String itemType,
            @RequestParam Long itemId) {
        if (currentUser == null) {
            return ResponseEntity.ok(ApiResponse.success(Map.of("favorited", false)));
        }
        boolean isFav = favoriteService.isFavorited(currentUser.getId(), itemType, itemId);
        return ResponseEntity.ok(ApiResponse.success(Map.of("favorited", isFav)));
    }
}
