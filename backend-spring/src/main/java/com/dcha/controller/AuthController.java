package com.dcha.controller;

import com.dcha.dto.ApiResponse;
import com.dcha.dto.AuthRequest;
import com.dcha.dto.AuthResponse;
import com.dcha.dto.RegisterRequest;
import com.dcha.dto.UpdateProfileRequest;
import com.dcha.security.UserPrincipal;
import com.dcha.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping({"/register", "/signup"})
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout() {
        return ResponseEntity.ok(ApiResponse.success("Logged out successfully"));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<AuthResponse.UserDto>> getCurrentUser(
            @AuthenticationPrincipal UserPrincipal currentUser) {
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("User is not authenticated"));
        }
        AuthResponse.UserDto user = authService.getCurrentUser(currentUser);
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<AuthResponse.UserDto>> updateProfile(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @Valid @RequestBody UpdateProfileRequest request) {
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("User is not authenticated"));
        }
        AuthResponse.UserDto updated = authService.updateProfile(currentUser, request);
        return ResponseEntity.ok(ApiResponse.success("Profile updated successfully", updated));
    }

    @GetMapping("/export-data")
    public ResponseEntity<ApiResponse<Map<String, Object>>> exportUserData(
            @AuthenticationPrincipal UserPrincipal currentUser) {
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("User is not authenticated"));
        }
        Map<String, Object> data = authService.exportUserData(currentUser);
        return ResponseEntity.ok(ApiResponse.success("Personal data export generated in accordance with DPDP Act 2023", data));
    }

    @DeleteMapping("/account")
    public ResponseEntity<ApiResponse<String>> deleteAccount(
            @AuthenticationPrincipal UserPrincipal currentUser) {
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("User is not authenticated"));
        }
        authService.deleteAccount(currentUser);
        return ResponseEntity.ok(ApiResponse.success("Account and associated personal records erased permanently."));
    }
}
