package com.electronics.store.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "attribute_type")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttributeTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;   // "Color", "Processor", "RAM"

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;
}