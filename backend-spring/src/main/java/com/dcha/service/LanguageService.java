package com.dcha.service;

import com.dcha.entity.Language;
import com.dcha.repository.LanguageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LanguageService {

    private final LanguageRepository languageRepository;

    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public List<Language> getAllLanguages(Long stateId) {
        if (stateId != null) {
            return languageRepository.findByStateId(stateId);
        }
        return languageRepository.findAll();
    }

    public Optional<Language> getLanguageById(Long id) {
        return languageRepository.findById(id);
    }
}
