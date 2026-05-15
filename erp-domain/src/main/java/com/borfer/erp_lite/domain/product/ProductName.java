package com.borfer.erp_lite.domain.product;

import java.util.Objects;

public record ProductName(String value) {

    public ProductName {
        Objects.requireNonNull(value, "ProductName cannot be null");
        if (value.length() < 3) {
            throw new IllegalArgumentException("ProductName must be at least 3 characters, got: " + value.length());
        }
        if (value.length() > 200) {
            throw new IllegalArgumentException("ProductName must be at most 200 characters, got: " + value.length());
        }
    }

    public static ProductName of(String value) {
        return new ProductName(value);
    }
}