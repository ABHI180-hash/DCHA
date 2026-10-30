package com.dcha.controller;

import com.dcha.dto.ApiResponse;
import com.dcha.entity.Festival;
import com.dcha.service.FestivalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/festivals")
public class FestivalController {

    private final FestivalService festivalService;

    public FestivalController(FestivalService festivalService) {
        this.festivalService = festivalService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Festival>>> getAllFestivals(
            @RequestParam(required = false) Long stateId,
            @RequestParam(required = false) Integer month) {
        List<Festival> list = festivalService.getAllFestivals(stateId, month);
        return ResponseEntity.ok(ApiResponse.success(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Festival>> getFestivalById(@PathVariable Long id) {
        return festivalService.getFestivalById(id)
                .map(item -> ResponseEntity.ok(ApiResponse.success(item)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
