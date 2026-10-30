package com.dcha.service;

import com.dcha.entity.HeritageSite;
import com.dcha.repository.HeritageSiteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HeritageSiteService {

    private final HeritageSiteRepository heritageSiteRepository;

    public HeritageSiteService(HeritageSiteRepository heritageSiteRepository) {
        this.heritageSiteRepository = heritageSiteRepository;
    }

    public List<HeritageSite> getAllHeritageSites() {
        return heritageSiteRepository.findAll();
    }

    public Optional<HeritageSite> getHeritageSiteBySlug(String slug) {
        return heritageSiteRepository.findBySlug(slug);
    }

    public List<HeritageSite> getHeritageSitesByState(Long stateId) {
        return heritageSiteRepository.findByStateId(stateId);
    }

    public List<HeritageSite> searchHeritageSites(String query) {
        return heritageSiteRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query);
    }
}
