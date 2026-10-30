package com.dcha.repository;

import com.dcha.entity.CollectionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionItemRepository extends JpaRepository<CollectionItem, Long> {
    List<CollectionItem> findByCollectionId(Long collectionId);
    void deleteByCollectionIdAndId(Long collectionId, Long itemId);
    void deleteByCollectionId(Long collectionId);
    boolean existsByCollectionIdAndItemTypeAndItemId(Long collectionId, String itemType, Long itemId);
}
