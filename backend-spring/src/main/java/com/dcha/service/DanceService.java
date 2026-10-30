package com.dcha.service;

import com.dcha.entity.Dance;
import com.dcha.repository.DanceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DanceService {

    private final DanceRepository danceRepository;

    public DanceService(DanceRepository danceRepository) {
        this.danceRepository = danceRepository;
    }

    public List<Dance> getAllDances(Long stateId, String category) {
        if (stateId != null) {
            return danceRepository.findByStateId(stateId);
        }
        if (category != null && !category.isBlank()) {
            return danceRepository.findByCategory(category);
        }
        return danceRepository.findAll();
    }

    public Optional<Dance> getDanceById(Long id) {
        return danceRepository.findById(id);
    }
}
