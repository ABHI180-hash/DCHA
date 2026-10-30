package com.dcha.repository;

import com.dcha.entity.MuseumArtifact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MuseumArtifactRepository extends JpaRepository<MuseumArtifact, Long> {
    List<MuseumArtifact> findByStateId(Long stateId);
    List<MuseumArtifact> findBySection(String section);
    List<MuseumArtifact> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description);
}
