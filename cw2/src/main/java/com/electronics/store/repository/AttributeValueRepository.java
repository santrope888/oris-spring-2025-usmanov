package com.electronics.store.repository;

import com.electronics.store.model.AttributeValueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttributeValueRepository extends JpaRepository<AttributeValueEntity, Long> {
    void deleteByProductId(Long productId);
    long countByAttributeTypeId(Long attributeTypeId);
}
