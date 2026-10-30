package com.dcha.service;

import com.dcha.entity.MuseumArtifact;
import com.dcha.repository.MuseumArtifactRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MuseumService {

    private final MuseumArtifactRepository artifactRepository;

    public MuseumService(MuseumArtifactRepository artifactRepository) {
        this.artifactRepository = artifactRepository;
    }

    public List<MuseumArtifact> getAllArtifacts(Long stateId, String section) {
        if (stateId != null) {
            return artifactRepository.findByStateId(stateId);
        }
        if (section != null && !section.isBlank()) {
            return artifactRepository.findBySection(section);
        }
        return artifactRepository.findAll();
    }

    public Optional<MuseumArtifact> getArtifactById(Long id) {
        return artifactRepository.findById(id);
    }
}
