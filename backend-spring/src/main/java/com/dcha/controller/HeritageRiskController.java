package com.dcha.controller;

import com.dcha.dto.ApiResponse;
import com.dcha.entity.HeritageRisk;
import com.dcha.service.HeritageRiskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/heritage-risk")
public class HeritageRiskController {

    private final HeritageRiskService heritageRiskService;

    public HeritageRiskController(HeritageRiskService heritageRiskService) {
        this.heritageRiskService = heritageRiskService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<HeritageRisk>>> getHeritageRisks(
            @RequestParam(required = false) Long stateId,
            @RequestParam(required = false) String riskLevel) {
        List<HeritageRisk> list = heritageRiskService.getHeritageRisks(stateId, riskLevel);
        return ResponseEntity.ok(ApiResponse.success(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HeritageRisk>> getHeritageRiskById(@PathVariable Long id) {
        HeritageRisk item = heritageRiskService.getHeritageRiskById(id);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ApiResponse.success(item));
    }
}
