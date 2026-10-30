package com.dcha.repository;

import com.dcha.entity.CulturalEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CulturalEventRepository extends JpaRepository<CulturalEvent, Long> {
    List<CulturalEvent> findByStateId(Long stateId);
    List<CulturalEvent> findByMonth(Integer month);
}
