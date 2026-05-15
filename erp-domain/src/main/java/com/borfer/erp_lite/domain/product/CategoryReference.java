package com.borfer.erp_lite.domain.product;

import java.util.Objects;

public record CategoryReference(String categoryId) {

    public CategoryReference {
        Objects.requireNonNull(categoryId, "CategoryId cannot be null");
        if (categoryId.isBlank()) throw new IllegalArgumentException("CategoryId cannot be blank");
    }

    public static CategoryReference of(String categoryId) {
        return new CategoryReference(categoryId);
    }
}
