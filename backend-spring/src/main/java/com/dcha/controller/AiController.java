package com.dcha.controller;

import com.dcha.dto.AiRequest;
import com.dcha.dto.AiResponse;
import com.dcha.service.AiService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/chat")
    public ResponseEntity<AiResponse> chat(@Valid @RequestBody AiRequest request) {
        AiResponse response = aiService.processAiQuery(request);
        return ResponseEntity.ok(response);
    }
}
