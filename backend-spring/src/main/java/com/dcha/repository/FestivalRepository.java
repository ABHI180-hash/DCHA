package com.dcha.repository;

import com.dcha.entity.Festival;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FestivalRepository extends JpaRepository<Festival, Long> {
    List<Festival> findByStateId(Long stateId);
    List<Festival> findByMonth(Integer month);
    List<Festival> findByNameContainingIgnoreCase(String name);
}
