package com.dcha.service;

import com.dcha.entity.HeritageRisk;
import com.dcha.repository.HeritageRiskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HeritageRiskService {

    private final HeritageRiskRepository heritageRiskRepository;

    public HeritageRiskService(HeritageRiskRepository heritageRiskRepository) {
        this.heritageRiskRepository = heritageRiskRepository;
    }

    @Transactional(readOnly = true)
    public List<HeritageRisk> getHeritageRisks(Long stateId, String riskLevel) {
        if (stateId != null) {
            return heritageRiskRepository.findByStateId(stateId);
        }
        if (riskLevel != null && !riskLevel.isBlank()) {
            return heritageRiskRepository.findByRiskLevel(riskLevel.toUpperCase());
        }
        return heritageRiskRepository.findAll();
    }

    @Transactional(readOnly = true)
    public HeritageRisk getHeritageRiskById(Long id) {
        return heritageRiskRepository.findById(id).orElse(null);
    }
}
