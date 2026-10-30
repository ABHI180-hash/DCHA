package com.dcha.repository;

import com.dcha.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {
    Optional<State> findBySlug(String slug);
    List<State> findByRegion(String region);
    List<State> findByType(String type);
    List<State> findByNameContainingIgnoreCase(String name);
}
