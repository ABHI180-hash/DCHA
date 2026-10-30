package com.dcha.repository;

import com.dcha.entity.HeritageSite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HeritageSiteRepository extends JpaRepository<HeritageSite, Long> {
    Optional<HeritageSite> findBySlug(String slug);
    List<HeritageSite> findByStateId(Long stateId);
    List<HeritageSite> findByCategory(String category);
    List<HeritageSite> findByNameContainingIgnoreCase(String name);
    List<HeritageSite> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String nameQuery, String descQuery);
}
