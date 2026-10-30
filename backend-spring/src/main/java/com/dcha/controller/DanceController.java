package com.dcha.controller;

import com.dcha.dto.ApiResponse;
import com.dcha.entity.Dance;
import com.dcha.service.DanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dances")
public class DanceController {

    private final DanceService danceService;

    public DanceController(DanceService danceService) {
        this.danceService = danceService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Dance>>> getAllDances(
            @RequestParam(required = false) Long stateId,
            @RequestParam(required = false) String category) {
        List<Dance> list = danceService.getAllDances(stateId, category);
        return ResponseEntity.ok(ApiResponse.success(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Dance>> getDanceById(@PathVariable Long id) {
        return danceService.getDanceById(id)
                .map(item -> ResponseEntity.ok(ApiResponse.success(item)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
