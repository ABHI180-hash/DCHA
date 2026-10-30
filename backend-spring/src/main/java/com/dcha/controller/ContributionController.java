package com.dcha.controller;

import com.dcha.dto.ApiResponse;
import com.dcha.entity.Contribution;
import com.dcha.entity.User;
import com.dcha.repository.ContributionRepository;
import com.dcha.repository.UserRepository;
import com.dcha.security.UserPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contributions")
public class ContributionController {

    private final ContributionRepository contributionRepository;
    private final UserRepository userRepository;

    public ContributionController(ContributionRepository contributionRepository, UserRepository userRepository) {
        this.contributionRepository = contributionRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<Contribution>>> getMyContributions(
            @AuthenticationPrincipal UserPrincipal currentUser) {
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<Contribution> list = contributionRepository.findByUserId(currentUser.getId());
        return ResponseEntity.ok(ApiResponse.success(list));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Contribution>> createContribution(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestBody Contribution contributionRequest) {
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        contributionRequest.setId(null);
        contributionRequest.setUser(user);
        contributionRequest.setStatus("PENDING");

        if (contributionRequest.getTitle() != null) {
            contributionRequest.setTitle(contributionRequest.getTitle().trim().replaceAll("<[^>]*>", ""));
        }
        if (contributionRequest.getDescription() != null) {
            contributionRequest.setDescription(contributionRequest.getDescription().trim().replaceAll("<[^>]*>", ""));
        }
        if (contributionRequest.getCategory() != null) {
            contributionRequest.setCategory(contributionRequest.getCategory().trim().replaceAll("<[^>]*>", ""));
        }

        Contribution saved = contributionRepository.save(contributionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Contribution submitted for review", saved));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('CURATOR')")
    public ResponseEntity<ApiResponse<List<Contribution>>> getAllContributions() {
        List<Contribution> list = contributionRepository.findAll();
        return ResponseEntity.ok(ApiResponse.success(list));
    }
}
