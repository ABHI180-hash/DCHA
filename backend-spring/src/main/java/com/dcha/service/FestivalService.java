package com.dcha.service;

import com.dcha.entity.Festival;
import com.dcha.repository.FestivalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FestivalService {

    private final FestivalRepository festivalRepository;

    public FestivalService(FestivalRepository festivalRepository) {
        this.festivalRepository = festivalRepository;
    }

    public List<Festival> getAllFestivals(Long stateId, Integer month) {
        if (stateId != null) {
            return festivalRepository.findByStateId(stateId);
        }
        if (month != null) {
            return festivalRepository.findByMonth(month);
        }
        return festivalRepository.findAll();
    }

    public Optional<Festival> getFestivalById(Long id) {
        return festivalRepository.findById(id);
    }
}
