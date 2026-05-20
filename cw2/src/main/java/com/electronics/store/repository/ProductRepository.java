package com.electronics.store.repository;

import com.electronics.store.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>, JpaSpecificationExecutor<ProductEntity> {

    @Query("SELECT DISTINCT av.value FROM AttributeValueEntity av WHERE av.attributeType.id = :attrTypeId " +
            "AND av.product.category.slug = :categorySlug")
    List<String> findDistinctAttributeValues(@Param("attrTypeId") Long attrTypeId,
                                             @Param("categorySlug") String categorySlug);
}
