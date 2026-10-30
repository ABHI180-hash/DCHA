package com.dcha.controller;

import com.dcha.dto.ApiResponse;
import com.dcha.entity.MuseumArtifact;
import com.dcha.service.MuseumService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/museum")
public class MuseumController {

    private final MuseumService museumService;

    public MuseumController(MuseumService museumService) {
        this.museumService = museumService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MuseumArtifact>>> getAllArtifacts(
            @RequestParam(required = false) Long stateId,
            @RequestParam(required = false) String section) {
        List<MuseumArtifact> list = museumService.getAllArtifacts(stateId, section);
        return ResponseEntity.ok(ApiResponse.success(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MuseumArtifact>> getArtifactById(@PathVariable Long id) {
        return museumService.getArtifactById(id)
                .map(item -> ResponseEntity.ok(ApiResponse.success(item)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
