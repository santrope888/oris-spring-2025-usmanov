package com.electronics.store.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String slug;        // для URL (например, "smartphones")
    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<ProductEntity> products = new ArrayList<>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<AttributeTypeEntity> allowedAttributeTypeEntities = new ArrayList<>();

}