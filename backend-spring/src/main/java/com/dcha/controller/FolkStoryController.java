package com.dcha.controller;

import com.dcha.dto.ApiResponse;
import com.dcha.entity.FolkStory;
import com.dcha.service.FolkStoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stories")
public class FolkStoryController {

    private final FolkStoryService folkStoryService;

    public FolkStoryController(FolkStoryService folkStoryService) {
        this.folkStoryService = folkStoryService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FolkStory>>> getAllStories(
            @RequestParam(required = false) Long stateId) {
        List<FolkStory> list = folkStoryService.getAllStories(stateId);
        return ResponseEntity.ok(ApiResponse.success(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FolkStory>> getStoryById(@PathVariable Long id) {
        return folkStoryService.getStoryById(id)
                .map(item -> ResponseEntity.ok(ApiResponse.success(item)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
