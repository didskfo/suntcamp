package com.example.suntcamp.specification;

import com.example.suntcamp.domain.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {
    public static Specification<Product> hasPriceGreaterThanOrEq(Integer min) {
        return (root, query, cb) -> min == null ? null : cb.greaterThanOrEqualTo(root.get("price"), min);
    }

    public static Specification<Product> hasPriceLessThanOrEq(Integer max) {
        return (root, query, cb) -> max == null ? null : cb.lessThanOrEqualTo(root.get("price"), max);
    }

    public static Specification<Product> hasKeyword(String keyword) {
        return (root, query, cb) -> keyword == null || keyword.isBlank() ? null :
                cb.like(cb.lower(root.get("name")), "%" + keyword.toLowerCase() + "%");
    }

    public static Specification<Product> hasCategory(Long categoryId) {
        return (root, query, cb) -> categoryId == null ? null :
                cb.equal(root.get("category").get("id"), categoryId);
    }
}
