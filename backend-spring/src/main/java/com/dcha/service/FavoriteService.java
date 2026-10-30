package com.dcha.service;

import com.dcha.dto.FavoriteRequest;
import com.dcha.entity.Favorite;
import com.dcha.entity.User;
import com.dcha.exception.BadRequestException;
import com.dcha.exception.ResourceNotFoundException;
import com.dcha.repository.FavoriteRepository;
import com.dcha.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;

    public FavoriteService(FavoriteRepository favoriteRepository, UserRepository userRepository) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<Favorite> getUserFavorites(Long userId) {
        return favoriteRepository.findByUserId(userId);
    }

    @Transactional
    public Favorite addFavorite(Long userId, FavoriteRequest request) {
        if (favoriteRepository.existsByUserIdAndItemTypeAndItemId(userId, request.getItemType(), request.getItemId())) {
            throw new BadRequestException("Item is already favorited");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setItemType(request.getItemType());
        favorite.setItemId(request.getItemId());

        return favoriteRepository.save(favorite);
    }

    @Transactional
    public void removeFavorite(Long userId, Long favoriteId) {
        Favorite favorite = favoriteRepository.findById(favoriteId)
                .orElseThrow(() -> new ResourceNotFoundException("Favorite item not found"));

        if (!favorite.getUser().getId().equals(userId)) {
            throw new BadRequestException("Unauthorized to remove this favorite");
        }

        favoriteRepository.delete(favorite);
    }

    @Transactional
    public void removeFavoriteByItem(Long userId, String itemType, Long itemId) {
        if (!favoriteRepository.existsByUserIdAndItemTypeAndItemId(userId, itemType, itemId)) {
            throw new ResourceNotFoundException("Favorite not found for specified item");
        }
        favoriteRepository.deleteByUserIdAndItemTypeAndItemId(userId, itemType, itemId);
    }

    @Transactional(readOnly = true)
    public boolean isFavorited(Long userId, String itemType, Long itemId) {
        return favoriteRepository.existsByUserIdAndItemTypeAndItemId(userId, itemType, itemId);
    }
}
