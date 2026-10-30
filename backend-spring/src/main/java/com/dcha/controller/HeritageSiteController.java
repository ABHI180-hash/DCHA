package com.dcha.controller;

import com.dcha.dto.ApiResponse;
import com.dcha.entity.HeritageSite;
import com.dcha.service.HeritageSiteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/heritage")
public class HeritageSiteController {

    private final HeritageSiteService heritageSiteService;

    public HeritageSiteController(HeritageSiteService heritageSiteService) {
        this.heritageSiteService = heritageSiteService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<HeritageSite>>> getAllHeritageSites() {
        List<HeritageSite> sites = heritageSiteService.getAllHeritageSites();
        return ResponseEntity.ok(ApiResponse.success(sites));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ApiResponse<HeritageSite>> getHeritageSiteBySlug(@PathVariable String slug) {
        return heritageSiteService.getHeritageSiteBySlug(slug)
                .map(site -> ResponseEntity.ok(ApiResponse.success(site)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/state/{stateId}")
    public ResponseEntity<ApiResponse<List<HeritageSite>>> getHeritageSitesByState(@PathVariable Long stateId) {
        List<HeritageSite> sites = heritageSiteService.getHeritageSitesByState(stateId);
        return ResponseEntity.ok(ApiResponse.success(sites));
    }
}
