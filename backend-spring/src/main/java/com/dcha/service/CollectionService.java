package com.dcha.service;

import com.dcha.dto.CollectionItemRequest;
import com.dcha.dto.CollectionRequest;
import com.dcha.entity.Collection;
import com.dcha.entity.CollectionItem;
import com.dcha.entity.User;
import com.dcha.exception.BadRequestException;
import com.dcha.exception.ResourceNotFoundException;
import com.dcha.repository.CollectionItemRepository;
import com.dcha.repository.CollectionRepository;
import com.dcha.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CollectionService {

    private final CollectionRepository collectionRepository;
    private final CollectionItemRepository collectionItemRepository;
    private final UserRepository userRepository;

    public CollectionService(CollectionRepository collectionRepository,
                             CollectionItemRepository collectionItemRepository,
                             UserRepository userRepository) {
        this.collectionRepository = collectionRepository;
        this.collectionItemRepository = collectionItemRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<Collection> getUserCollections(Long userId) {
        return collectionRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public Collection getCollectionById(Long userId, Long collectionId) {
        Collection collection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Collection not found"));

        if (!collection.getIsPublic() && !collection.getUser().getId().equals(userId)) {
            throw new BadRequestException("Unauthorized access to private collection");
        }
        return collection;
    }

    @Transactional
    public Collection createCollection(Long userId, CollectionRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Collection collection = new Collection();
        collection.setUser(user);
        collection.setName(request.getName());
        collection.setDescription(request.getDescription());
        collection.setIsPublic(request.getIsPublic() != null ? request.getIsPublic() : true);

        return collectionRepository.save(collection);
    }

    @Transactional
    public Collection updateCollection(Long userId, Long collectionId, CollectionRequest request) {
        Collection collection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Collection not found"));

        if (!collection.getUser().getId().equals(userId)) {
            throw new BadRequestException("Unauthorized to modify this collection");
        }

        if (request.getName() != null && !request.getName().isBlank()) {
            collection.setName(request.getName());
        }
        if (request.getDescription() != null) {
            collection.setDescription(request.getDescription());
        }
        if (request.getIsPublic() != null) {
            collection.setIsPublic(request.getIsPublic());
        }

        return collectionRepository.save(collection);
    }

    @Transactional
    public void deleteCollection(Long userId, Long collectionId) {
        Collection collection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Collection not found"));

        if (!collection.getUser().getId().equals(userId)) {
            throw new BadRequestException("Unauthorized to delete this collection");
        }

        collectionRepository.delete(collection);
    }

    @Transactional(readOnly = true)
    public List<CollectionItem> getCollectionItems(Long userId, Long collectionId) {
        Collection collection = getCollectionById(userId, collectionId);
        return collectionItemRepository.findByCollectionId(collection.getId());
    }

    @Transactional
    public CollectionItem addItemToCollection(Long userId, Long collectionId, CollectionItemRequest request) {
        Collection collection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Collection not found"));

        if (!collection.getUser().getId().equals(userId)) {
            throw new BadRequestException("Unauthorized to modify this collection");
        }

        if (collectionItemRepository.existsByCollectionIdAndItemTypeAndItemId(collectionId, request.getItemType(), request.getItemId())) {
            throw new BadRequestException("Already in this collection");
        }

        CollectionItem item = new CollectionItem();
        item.setCollection(collection);
        item.setItemType(request.getItemType());
        item.setItemId(request.getItemId());

        return collectionItemRepository.save(item);
    }

    @Transactional
    public void removeItemFromCollection(Long userId, Long collectionId, Long itemId) {
        Collection collection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Collection not found"));

        if (!collection.getUser().getId().equals(userId)) {
            throw new BadRequestException("Unauthorized to modify this collection");
        }

        collectionItemRepository.deleteByCollectionIdAndId(collectionId, itemId);
    }
}
