package com.electronics.store.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "attribute_value")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttributeValueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "attribute_type_id")
    private AttributeTypeEntity attributeType;

    private String value;   // например "Green", "Intel i7", "16GB"
}
