package com.dcha.repository;

import com.dcha.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByStateId(Long stateId);
    List<Food> findByCategory(String category);
    List<Food> findByNameContainingIgnoreCase(String name);
}
