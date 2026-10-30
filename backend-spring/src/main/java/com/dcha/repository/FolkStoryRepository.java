package com.dcha.repository;

import com.dcha.entity.FolkStory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolkStoryRepository extends JpaRepository<FolkStory, Long> {
    List<FolkStory> findByStateId(Long stateId);
    List<FolkStory> findByTitleContainingIgnoreCaseOrStoryTextContainingIgnoreCase(String title, String storyText);
}
