package com.dcha.service;

import com.dcha.entity.Food;
import com.dcha.repository.FoodRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodService {

    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public List<Food> getAllFoods(Long stateId, String category) {
        if (stateId != null) {
            return foodRepository.findByStateId(stateId);
        }
        if (category != null && !category.isBlank()) {
            return foodRepository.findByCategory(category);
        }
        return foodRepository.findAll();
    }

    public Optional<Food> getFoodById(Long id) {
        return foodRepository.findById(id);
    }
}
