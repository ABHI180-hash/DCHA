package com.dcha.service;

import com.dcha.entity.TraditionalDress;
import com.dcha.repository.TraditionalDressRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DressService {

    private final TraditionalDressRepository dressRepository;

    public DressService(TraditionalDressRepository dressRepository) {
        this.dressRepository = dressRepository;
    }

    public List<TraditionalDress> getAllDresses(Long stateId, String gender) {
        if (stateId != null) {
            return dressRepository.findByStateId(stateId);
        }
        if (gender != null && !gender.isBlank()) {
            return dressRepository.findByGender(gender);
        }
        return dressRepository.findAll();
    }

    public Optional<TraditionalDress> getDressById(Long id) {
        return dressRepository.findById(id);
    }
}
