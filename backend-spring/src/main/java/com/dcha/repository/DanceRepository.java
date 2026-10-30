package com.dcha.repository;

import com.dcha.entity.Dance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DanceRepository extends JpaRepository<Dance, Long> {
    List<Dance> findByStateId(Long stateId);
    List<Dance> findByCategory(String category);
    List<Dance> findByNameContainingIgnoreCase(String name);
}
