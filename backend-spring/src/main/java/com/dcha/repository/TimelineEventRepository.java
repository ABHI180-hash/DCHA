package com.dcha.repository;

import com.dcha.entity.TimelineEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimelineEventRepository extends JpaRepository<TimelineEvent, Long> {
    List<TimelineEvent> findByStateId(Long stateId);
    List<TimelineEvent> findByEra(String era);
    List<TimelineEvent> findByTitleContainingIgnoreCase(String title);
}
