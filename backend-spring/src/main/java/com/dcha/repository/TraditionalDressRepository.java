package com.dcha.repository;

import com.dcha.entity.TraditionalDress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TraditionalDressRepository extends JpaRepository<TraditionalDress, Long> {
    List<TraditionalDress> findByStateId(Long stateId);
    List<TraditionalDress> findByGender(String gender);
    List<TraditionalDress> findByNameContainingIgnoreCase(String name);
}
