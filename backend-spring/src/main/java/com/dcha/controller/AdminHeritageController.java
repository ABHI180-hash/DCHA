package com.dcha.controller;

import com.dcha.dto.ApiResponse;
import com.dcha.service.AdminHeritageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/heritage")
@PreAuthorize("hasRole('ADMIN')")
public class AdminHeritageController {

    private final AdminHeritageService adminHeritageService;

    public AdminHeritageController(AdminHeritageService adminHeritageService) {
        this.adminHeritageService = adminHeritageService;
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getAllAdminHeritageContent() {
        List<Map<String, Object>> content = adminHeritageService.getAllAdminHeritageContent();
        return ResponseEntity.ok(ApiResponse.success(content));
    }

    @PostMapping("/{category}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> createHeritageRecord(
            @PathVariable String category,
            @RequestBody Map<String, Object> body) {
        Map<String, Object> created = adminHeritageService.createHeritageRecord(category, body);
        return ResponseEntity.ok(ApiResponse.success("Heritage record created successfully", created));
    }

    @PutMapping("/{category}/{id}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> updateHeritageRecord(
            @PathVariable String category,
            @PathVariable Long id,
            @RequestBody Map<String, Object> body) {
        adminHeritageService.deleteHeritageRecord(category, id);
        Map<String, Object> updated = adminHeritageService.createHeritageRecord(category, body);
        return ResponseEntity.ok(ApiResponse.success("Heritage record updated successfully", updated));
    }

    @DeleteMapping("/{category}/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteHeritageRecord(
            @PathVariable String category,
            @PathVariable Long id) {
        adminHeritageService.deleteHeritageRecord(category, id);
        return ResponseEntity.ok(ApiResponse.success("Heritage record deleted successfully", null));
    }
}
