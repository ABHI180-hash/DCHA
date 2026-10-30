package com.dcha.controller;

import com.dcha.dto.ApiResponse;
import com.dcha.entity.*;
import com.dcha.service.StateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/states")
public class StateController {

    private final StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<State>>> getAllStates(
            @RequestParam(required = false) String region) {
        List<State> states = (region != null && !region.isBlank()) ?
                stateService.getStatesByRegion(region) : stateService.getAllStates();
        return ResponseEntity.ok(ApiResponse.success(states));
    }

    @GetMapping("/{idOrSlug}")
    public ResponseEntity<ApiResponse<State>> getStateByIdOrSlug(@PathVariable String idOrSlug) {
        return stateService.getStateByIdOrSlug(idOrSlug)
                .map(state -> ResponseEntity.ok(ApiResponse.success(state)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{idOrSlug}/heritage")
    public ResponseEntity<ApiResponse<List<HeritageSite>>> getStateHeritage(@PathVariable String idOrSlug) {
        return stateService.getStateByIdOrSlug(idOrSlug)
                .map(state -> ResponseEntity.ok(ApiResponse.success(stateService.getStateHeritageSites(state.getId()))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{idOrSlug}/festivals")
    public ResponseEntity<ApiResponse<List<Festival>>> getStateFestivals(@PathVariable String idOrSlug) {
        return stateService.getStateByIdOrSlug(idOrSlug)
                .map(state -> ResponseEntity.ok(ApiResponse.success(stateService.getStateFestivals(state.getId()))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{idOrSlug}/foods")
    public ResponseEntity<ApiResponse<List<Food>>> getStateFoods(@PathVariable String idOrSlug) {
        return stateService.getStateByIdOrSlug(idOrSlug)
                .map(state -> ResponseEntity.ok(ApiResponse.success(stateService.getStateFoods(state.getId()))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{idOrSlug}/dresses")
    public ResponseEntity<ApiResponse<List<TraditionalDress>>> getStateDresses(@PathVariable String idOrSlug) {
        return stateService.getStateByIdOrSlug(idOrSlug)
                .map(state -> ResponseEntity.ok(ApiResponse.success(stateService.getStateDresses(state.getId()))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{idOrSlug}/dances")
    public ResponseEntity<ApiResponse<List<Dance>>> getStateDances(@PathVariable String idOrSlug) {
        return stateService.getStateByIdOrSlug(idOrSlug)
                .map(state -> ResponseEntity.ok(ApiResponse.success(stateService.getStateDances(state.getId()))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{idOrSlug}/languages")
    public ResponseEntity<ApiResponse<List<Language>>> getStateLanguages(@PathVariable String idOrSlug) {
        return stateService.getStateByIdOrSlug(idOrSlug)
                .map(state -> ResponseEntity.ok(ApiResponse.success(stateService.getStateLanguages(state.getId()))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{idOrSlug}/stories")
    public ResponseEntity<ApiResponse<List<FolkStory>>> getStateStories(@PathVariable String idOrSlug) {
        return stateService.getStateByIdOrSlug(idOrSlug)
                .map(state -> ResponseEntity.ok(ApiResponse.success(stateService.getStateStories(state.getId()))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{idOrSlug}/museums")
    public ResponseEntity<ApiResponse<List<MuseumArtifact>>> getStateMuseums(@PathVariable String idOrSlug) {
        return stateService.getStateByIdOrSlug(idOrSlug)
                .map(state -> ResponseEntity.ok(ApiResponse.success(stateService.getStateMuseumArtifacts(state.getId()))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
