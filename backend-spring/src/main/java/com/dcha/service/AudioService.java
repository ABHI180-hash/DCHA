package com.dcha.service;

import com.dcha.entity.AudioItem;
import com.dcha.repository.AudioItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AudioService {

    private final AudioItemRepository audioItemRepository;

    public AudioService(AudioItemRepository audioItemRepository) {
        this.audioItemRepository = audioItemRepository;
    }

    public List<AudioItem> getAllAudio(Long stateId, String category) {
        if (stateId != null) {
            return audioItemRepository.findByStateId(stateId);
        }
        if (category != null && !category.isBlank()) {
            return audioItemRepository.findByCategory(category);
        }
        return audioItemRepository.findAll();
    }

    public Optional<AudioItem> getAudioById(Long id) {
        return audioItemRepository.findById(id);
    }
}
