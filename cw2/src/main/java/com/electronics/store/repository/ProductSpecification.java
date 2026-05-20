package com.electronics.store.repository;

import com.electronics.store.model.ProductEntity;
import com.electronics.store.model.AttributeValueEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;
import java.util.Map;

public class ProductSpecification {

    public static Specification<ProductEntity> byCategorySlug(String categorySlug) {
        return (root, query, cb) -> cb.equal(root.get("category").get("slug"), categorySlug);
    }

    public static Specification<ProductEntity> byAttributeFilters(Map<Long, List<String>> filters) {
        return (root, query, cb) -> {
            if (filters == null || filters.isEmpty()) return cb.conjunction();
            query.distinct(true);
            Join<ProductEntity, AttributeValueEntity> attrs = root.join("attributeValues");
            Predicate predicate = cb.disjunction();
            for (Map.Entry<Long, List<String>> entry : filters.entrySet()) {
                Long attrTypeId = entry.getKey();
                List<String> values = entry.getValue();
                if (values != null && !values.isEmpty()) {
                    Predicate typePredicate = cb.equal(attrs.get("attributeType").get("id"), attrTypeId);
                    Predicate valuesPredicate = attrs.get("value").in(values);
                    predicate = cb.or(predicate, cb.and(typePredicate, valuesPredicate));
                }
            }
            return predicate;
        };
    }
}