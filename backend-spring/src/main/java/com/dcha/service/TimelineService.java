package com.dcha.service;

import com.dcha.entity.TimelineEvent;
import com.dcha.repository.TimelineEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TimelineService {

    private final TimelineEventRepository timelineEventRepository;

    public TimelineService(TimelineEventRepository timelineEventRepository) {
        this.timelineEventRepository = timelineEventRepository;
    }

    @Transactional(readOnly = true)
    public List<TimelineEvent> getTimelineEvents(Long stateId, String era, String search) {
        if (stateId != null) {
            return timelineEventRepository.findByStateId(stateId);
        }
        if (era != null && !era.isBlank()) {
            return timelineEventRepository.findByEra(era);
        }
        if (search != null && !search.isBlank()) {
            return timelineEventRepository.findByTitleContainingIgnoreCase(search);
        }
        return timelineEventRepository.findAll();
    }

    @Transactional(readOnly = true)
    public TimelineEvent getTimelineEventById(Long id) {
        return timelineEventRepository.findById(id).orElse(null);
    }
}
