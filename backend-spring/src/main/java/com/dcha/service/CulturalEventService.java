package com.dcha.service;

import com.dcha.entity.CulturalEvent;
import com.dcha.repository.CulturalEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CulturalEventService {

    private final CulturalEventRepository culturalEventRepository;

    public CulturalEventService(CulturalEventRepository culturalEventRepository) {
        this.culturalEventRepository = culturalEventRepository;
    }

    @Transactional(readOnly = true)
    public List<CulturalEvent> getEvents(Long stateId, Integer month) {
        if (stateId != null) {
            return culturalEventRepository.findByStateId(stateId);
        }
        if (month != null) {
            return culturalEventRepository.findByMonth(month);
        }
        return culturalEventRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CulturalEvent getEventById(Long id) {
        return culturalEventRepository.findById(id).orElse(null);
    }
}
