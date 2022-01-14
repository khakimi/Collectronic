package com.example.collectronic.repository;

import com.example.collectronic.entity.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<ImageModel, Long> {
    Optional<ImageModel> findByUserId(Long userId);
    Optional<ImageModel> findByUserCollectionId(Long userCollectionId);
    Optional<ImageModel> findByItemId(Long itemId);
}
