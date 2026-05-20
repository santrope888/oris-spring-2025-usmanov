package com.electronics.store.repository;

import com.electronics.store.model.AttributeTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttributeTypeRepository extends JpaRepository<AttributeTypeEntity, Long> {
    List<AttributeTypeEntity> findByCategoryId(Long categoryId);
}
