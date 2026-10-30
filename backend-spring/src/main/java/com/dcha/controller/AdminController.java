package com.dcha.controller;

import com.dcha.dto.ApiResponse;
import com.dcha.dto.ContributionStatusRequest;
import com.dcha.entity.Contribution;
import com.dcha.security.UserPrincipal;
import com.dcha.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN') or hasRole('CURATOR')")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getDashboardStats() {
        Map<String, Object> stats = adminService.getDashboardStats();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    @GetMapping("/contributions")
    public ResponseEntity<ApiResponse<List<Contribution>>> getAllContributions(@RequestParam(required = false) String status) {
        List<Contribution> list = adminService.getAllContributions(status);
        return ResponseEntity.ok(ApiResponse.success(list));
    }

    @PutMapping("/contributions/{id}/status")
    public ResponseEntity<ApiResponse<Contribution>> reviewContribution(
            @AuthenticationPrincipal UserPrincipal reviewer,
            @PathVariable Long id,
            @Valid @RequestBody ContributionStatusRequest request) {
        Contribution updated = adminService.reviewContribution(id, reviewer.getId(), request);
        return ResponseEntity.ok(ApiResponse.success("Contribution status updated", updated));
    }
}
