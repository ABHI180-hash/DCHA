package com.dcha.service;

import com.dcha.dto.ContributionStatusRequest;
import com.dcha.entity.Contribution;
import com.dcha.entity.User;
import com.dcha.exception.ResourceNotFoundException;
import com.dcha.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final HeritageSiteRepository heritageSiteRepository;
    private final StateRepository stateRepository;
    private final FestivalRepository festivalRepository;
    private final ContributionRepository contributionRepository;

    public AdminService(UserRepository userRepository,
                        HeritageSiteRepository heritageSiteRepository,
                        StateRepository stateRepository,
                        FestivalRepository festivalRepository,
                        ContributionRepository contributionRepository) {
        this.userRepository = userRepository;
        this.heritageSiteRepository = heritageSiteRepository;
        this.stateRepository = stateRepository;
        this.festivalRepository = festivalRepository;
        this.contributionRepository = contributionRepository;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", userRepository.count());
        stats.put("totalHeritageSites", heritageSiteRepository.count());
        stats.put("totalStates", stateRepository.count());
        stats.put("totalFestivals", festivalRepository.count());
        stats.put("totalContributions", contributionRepository.count());

        List<Contribution> pending = contributionRepository.findByStatus("PENDING");
        stats.put("pendingContributions", pending.size());
        List<Contribution> approved = contributionRepository.findByStatus("APPROVED");
        stats.put("approvedContributions", approved.size());
        List<Contribution> rejected = contributionRepository.findByStatus("REJECTED");
        stats.put("rejectedContributions", rejected.size());

        return stats;
    }

    @Transactional(readOnly = true)
    public List<Contribution> getAllContributions(String status) {
        if (status != null && !status.isBlank()) {
            return contributionRepository.findByStatus(status.toUpperCase());
        }
        return contributionRepository.findAll();
    }

    @Transactional
    public Contribution reviewContribution(Long contributionId, Long reviewerId, ContributionStatusRequest request) {
        Contribution contribution = contributionRepository.findById(contributionId)
                .orElseThrow(() -> new ResourceNotFoundException("Contribution not found"));

        User reviewer = userRepository.findById(reviewerId)
                .orElseThrow(() -> new ResourceNotFoundException("Reviewer not found"));

        contribution.setStatus(request.getStatus().toUpperCase());
        if (request.getReviewerNotes() != null) {
            contribution.setReviewerNotes(request.getReviewerNotes());
        }
        contribution.setReviewedBy(reviewer);

        return contributionRepository.save(contribution);
    }
}
