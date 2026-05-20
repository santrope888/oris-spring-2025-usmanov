package com.electronics.store.service.impl;

import com.electronics.store.model.*;
import com.electronics.store.repository.CategoryRepository;
import com.electronics.store.repository.ProductRepository;
import com.electronics.store.repository.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CatalogService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CatalogService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<CategoryEntity> getCategoryBySlug(String slug) {
        return categoryRepository.findBySlug(slug);
    }

    @Transactional(readOnly = true)
    public Page<ProductEntity> getProductsByCategory(String categorySlug, Map<Long, List<String>> filters, Pageable pageable) {
        Specification<ProductEntity> spec = Specification.where(ProductSpecification.byCategorySlug(categorySlug));
        if (filters != null && !filters.isEmpty()) {
            spec = spec.and(ProductSpecification.byAttributeFilters(filters));
        }
        return productRepository.findAll(spec, pageable);
    }

    @Transactional(readOnly = true)
    public Map<AttributeTypeEntity, List<String>> getAvailableFiltersForCategory(String categorySlug) {
        // Загружаем категорию с её allowedAttributeTypes
        CategoryEntity category = categoryRepository.findBySlug(categorySlug)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Для каждого типа характеристики получаем список уникальных значений среди товаров этой категории
        Map<AttributeTypeEntity, List<String>> filterOptions = new LinkedHashMap<>();
        for (AttributeTypeEntity attrType : category.getAllowedAttributeTypeEntities()) {
            // Выполняем запрос: SELECT DISTINCT av.value FROM AttributeValue av WHERE av.attributeType = attrType AND av.product.category.slug = categorySlug
            List<String> distinctValues = getDistinctAttributeValuesForType(attrType.getId(), categorySlug);
            if (!distinctValues.isEmpty()) {
                filterOptions.put(attrType, distinctValues);
            }
        }
        return filterOptions;
    }

    @Transactional(readOnly = true)
    public List<String> getDistinctAttributeValuesForType(Long attributeTypeId, String categorySlug) {
        // Можно через @Query в репозитории, но для простоты используем JPQL вручную
        return productRepository.findDistinctAttributeValues(attributeTypeId, categorySlug);
    }
}