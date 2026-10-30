package com.dcha.controller;

import com.dcha.dto.ApiResponse;
import com.dcha.entity.*;
import com.dcha.repository.DanceRepository;
import com.dcha.repository.FestivalRepository;
import com.dcha.repository.FoodRepository;
import com.dcha.service.HeritageSiteService;
import com.dcha.service.StateService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final StateService stateService;
    private final HeritageSiteService heritageSiteService;
    private final FestivalRepository festivalRepository;
    private final FoodRepository foodRepository;
    private final DanceRepository danceRepository;

    public SearchController(StateService stateService,
                            HeritageSiteService heritageSiteService,
                            FestivalRepository festivalRepository,
                            FoodRepository foodRepository,
                            DanceRepository danceRepository) {
        this.stateService = stateService;
        this.heritageSiteService = heritageSiteService;
        this.festivalRepository = festivalRepository;
        this.foodRepository = foodRepository;
        this.danceRepository = danceRepository;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<SearchResults>> search(
            @RequestParam(name = "q", defaultValue = "") String query,
            @RequestParam(name = "state", required = false) String stateParam,
            @RequestParam(name = "category", required = false) String categoryParam) {

        if (query.isBlank() && (stateParam == null || stateParam.isBlank()) && (categoryParam == null || categoryParam.isBlank())) {
            return ResponseEntity.ok(ApiResponse.success(new SearchResults("", 0, List.of(), List.of(), List.of(), List.of(), List.of())));
        }

        String searchTerm = query.trim();

        List<State> states = !searchTerm.isEmpty() ? stateService.searchStatesByName(searchTerm) : List.of();
        List<HeritageSite> heritageSites = !searchTerm.isEmpty() ? heritageSiteService.searchHeritageSites(searchTerm) : List.of();
        List<Festival> festivals = !searchTerm.isEmpty() ? festivalRepository.findByNameContainingIgnoreCase(searchTerm) : List.of();
        List<Food> foods = !searchTerm.isEmpty() ? foodRepository.findByNameContainingIgnoreCase(searchTerm) : List.of();
        List<Dance> dances = !searchTerm.isEmpty() ? danceRepository.findByNameContainingIgnoreCase(searchTerm) : List.of();

        int total = states.size() + heritageSites.size() + festivals.size() + foods.size() + dances.size();

        SearchResults results = new SearchResults(searchTerm, total, states, heritageSites, festivals, foods, dances);

        return ResponseEntity.ok(ApiResponse.success(results));
    }

    @Getter
    @Setter
    public static class SearchResults {
        private String query;
        private Integer totalResults;
        private List<State> states;
        private List<HeritageSite> heritageSites;
        private List<Festival> festivals;
        private List<Food> foods;
        private List<Dance> dances;

        public SearchResults() {}

        public SearchResults(String query, Integer totalResults, List<State> states, List<HeritageSite> heritageSites, List<Festival> festivals, List<Food> foods, List<Dance> dances) {
            this.query = query;
            this.totalResults = totalResults;
            this.states = states;
            this.heritageSites = heritageSites;
            this.festivals = festivals;
            this.foods = foods;
            this.dances = dances;
        }
        public String getQuery() { return query; }
        public void setQuery(String query) { this.query = query; }

        public Integer getTotalResults() { return totalResults; }
        public void setTotalResults(Integer totalResults) { this.totalResults = totalResults; }

        public List<State> getStates() { return states; }
        public void setStates(List<State> states) { this.states = states; }

        public List<HeritageSite> getHeritageSites() { return heritageSites; }
        public void setHeritageSites(List<HeritageSite> heritageSites) { this.heritageSites = heritageSites; }

        public List<Festival> getFestivals() { return festivals; }
        public void setFestivals(List<Festival> festivals) { this.festivals = festivals; }

        public List<Food> getFoods() { return foods; }
        public void setFoods(List<Food> foods) { this.foods = foods; }

        public List<Dance> getDances() { return dances; }
        public void setDances(List<Dance> dances) { this.dances = dances; }
    }
}
