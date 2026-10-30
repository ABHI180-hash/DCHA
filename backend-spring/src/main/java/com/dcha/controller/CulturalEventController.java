package com.dcha.controller;

import com.dcha.dto.ApiResponse;
import com.dcha.entity.CulturalEvent;
import com.dcha.service.CulturalEventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calendar")
public class CulturalEventController {

    private final CulturalEventService culturalEventService;

    public CulturalEventController(CulturalEventService culturalEventService) {
        this.culturalEventService = culturalEventService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CulturalEvent>>> getEvents(
            @RequestParam(required = false) Long stateId,
            @RequestParam(required = false) Integer month) {
        List<CulturalEvent> list = culturalEventService.getEvents(stateId, month);
        return ResponseEntity.ok(ApiResponse.success(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CulturalEvent>> getEventById(@PathVariable Long id) {
        CulturalEvent event = culturalEventService.getEventById(id);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ApiResponse.success(event));
    }
}
