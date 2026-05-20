package com.electronics.store.service.impl;

import com.electronics.store.model.*;
import com.electronics.store.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final AttributeTypeRepository attributeTypeRepository;
    private final AttributeValueRepository attributeValueRepository;

    public AdminService(CategoryRepository categoryRepository,
                        ProductRepository productRepository,
                        AttributeTypeRepository attributeTypeRepository,
                        AttributeValueRepository attributeValueRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.attributeTypeRepository = attributeTypeRepository;
        this.attributeValueRepository = attributeValueRepository;
    }

    // === Categories ===
    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<CategoryEntity> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Transactional
    public CategoryEntity saveCategory(CategoryEntity category) {
        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    // === Products ===
    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<ProductEntity> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public ProductEntity saveProduct(ProductEntity product) {
        return productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        // сначала удаляем все значения характеристик
        attributeValueRepository.deleteByProductId(id);
        productRepository.deleteById(id);
    }

    // === AttributeTypes ===
    public List<AttributeTypeEntity> getAllAttributeTypes() {
        return attributeTypeRepository.findAll();
    }

    public List<AttributeTypeEntity> getAttributeTypesByCategory(Long categoryId) {
        return attributeTypeRepository.findByCategoryId(categoryId);
    }

    public Optional<AttributeTypeEntity> getAttributeTypeById(Long id) {
        return attributeTypeRepository.findById(id);
    }

    @Transactional
    public AttributeTypeEntity saveAttributeType(AttributeTypeEntity attributeType) {
        return attributeTypeRepository.save(attributeType);
    }

    @Transactional
    public void deleteAttributeType(Long id) {
        // проверяем, есть ли связанные значения
        if (attributeValueRepository.countByAttributeTypeId(id) > 0) {
            throw new IllegalStateException("Нельзя удалить тип характеристики, у которого есть значения");
        }
        attributeTypeRepository.deleteById(id);
    }

    // === AttributeValues ===
    @Transactional
    public void updateProductAttributes(Long productId, List<AttributeValueEntity> attributeValues) {
        attributeValueRepository.deleteByProductId(productId);
        for (AttributeValueEntity av : attributeValues) {
            av.setProduct(productRepository.getReferenceById(productId));
            attributeValueRepository.save(av);
        }
    }
}