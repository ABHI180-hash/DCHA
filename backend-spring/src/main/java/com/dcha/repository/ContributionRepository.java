package com.dcha.repository;

import com.dcha.entity.Contribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContributionRepository extends JpaRepository<Contribution, Long> {
    List<Contribution> findByUserId(Long userId);
    List<Contribution> findByStatus(String status);
    void deleteByUserId(Long userId);
}
