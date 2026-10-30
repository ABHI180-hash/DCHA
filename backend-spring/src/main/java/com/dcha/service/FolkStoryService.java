package com.dcha.service;

import com.dcha.entity.FolkStory;
import com.dcha.repository.FolkStoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FolkStoryService {

    private final FolkStoryRepository folkStoryRepository;

    public FolkStoryService(FolkStoryRepository folkStoryRepository) {
        this.folkStoryRepository = folkStoryRepository;
    }

    public List<FolkStory> getAllStories(Long stateId) {
        if (stateId != null) {
            return folkStoryRepository.findByStateId(stateId);
        }
        return folkStoryRepository.findAll();
    }

    public Optional<FolkStory> getStoryById(Long id) {
        return folkStoryRepository.findById(id);
    }
}
