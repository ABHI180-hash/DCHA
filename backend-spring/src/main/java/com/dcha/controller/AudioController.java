package com.dcha.controller;

import com.dcha.dto.ApiResponse;
import com.dcha.entity.AudioItem;
import com.dcha.service.AudioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audio")
public class AudioController {

    private final AudioService audioService;

    public AudioController(AudioService audioService) {
        this.audioService = audioService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AudioItem>>> getAllAudio(
            @RequestParam(required = false) Long stateId,
            @RequestParam(required = false) String category) {
        List<AudioItem> list = audioService.getAllAudio(stateId, category);
        return ResponseEntity.ok(ApiResponse.success(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AudioItem>> getAudioById(@PathVariable Long id) {
        return audioService.getAudioById(id)
                .map(item -> ResponseEntity.ok(ApiResponse.success(item)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
