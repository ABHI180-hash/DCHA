package com.dcha.controller;

import com.dcha.dto.ApiResponse;
import com.dcha.entity.Language;
import com.dcha.service.LanguageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/languages")
public class LanguageController {

    private final LanguageService languageService;

    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Language>>> getAllLanguages(
            @RequestParam(required = false) Long stateId) {
        List<Language> list = languageService.getAllLanguages(stateId);
        return ResponseEntity.ok(ApiResponse.success(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Language>> getLanguageById(@PathVariable Long id) {
        return languageService.getLanguageById(id)
                .map(item -> ResponseEntity.ok(ApiResponse.success(item)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
