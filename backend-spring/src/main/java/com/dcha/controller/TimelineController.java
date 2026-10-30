package com.dcha.controller;

import com.dcha.dto.ApiResponse;
import com.dcha.entity.TimelineEvent;
import com.dcha.service.TimelineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timeline")
public class TimelineController {

    private final TimelineService timelineService;

    public TimelineController(TimelineService timelineService) {
        this.timelineService = timelineService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TimelineEvent>>> getTimelineEvents(
            @RequestParam(required = false) Long stateId,
            @RequestParam(required = false) String era,
            @RequestParam(required = false) String search) {
        List<TimelineEvent> list = timelineService.getTimelineEvents(stateId, era, search);
        return ResponseEntity.ok(ApiResponse.success(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TimelineEvent>> getTimelineEventById(@PathVariable Long id) {
        TimelineEvent event = timelineService.getTimelineEventById(id);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ApiResponse.success(event));
    }
}
