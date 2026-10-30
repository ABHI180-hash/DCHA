package com.dcha.controller;

import com.dcha.dto.ApiResponse;
import com.dcha.entity.TraditionalDress;
import com.dcha.service.DressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dresses")
public class DressController {

    private final DressService dressService;

    public DressController(DressService dressService) {
        this.dressService = dressService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TraditionalDress>>> getAllDresses(
            @RequestParam(required = false) Long stateId,
            @RequestParam(required = false) String gender) {
        List<TraditionalDress> list = dressService.getAllDresses(stateId, gender);
        return ResponseEntity.ok(ApiResponse.success(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TraditionalDress>> getDressById(@PathVariable Long id) {
        return dressService.getDressById(id)
                .map(item -> ResponseEntity.ok(ApiResponse.success(item)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
