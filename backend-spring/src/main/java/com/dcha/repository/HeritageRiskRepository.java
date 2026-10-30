package com.dcha.repository;

import com.dcha.entity.HeritageRisk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeritageRiskRepository extends JpaRepository<HeritageRisk, Long> {
    List<HeritageRisk> findByStateId(Long stateId);
    List<HeritageRisk> findByRiskLevel(String riskLevel);
}
