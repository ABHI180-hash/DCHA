package com.dcha.repository;

import com.dcha.entity.AudioItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AudioItemRepository extends JpaRepository<AudioItem, Long> {
    List<AudioItem> findByStateId(Long stateId);
    List<AudioItem> findByCategory(String category);
    List<AudioItem> findByTitleContainingIgnoreCase(String title);
}
