package com.dcha.controller;

import com.dcha.dto.ApiResponse;
import com.dcha.entity.Food;
import com.dcha.service.FoodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Food>>> getAllFoods(
            @RequestParam(required = false) Long stateId,
            @RequestParam(required = false) String category) {
        List<Food> list = foodService.getAllFoods(stateId, category);
        return ResponseEntity.ok(ApiResponse.success(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Food>> getFoodById(@PathVariable Long id) {
        return foodService.getFoodById(id)
                .map(item -> ResponseEntity.ok(ApiResponse.success(item)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
