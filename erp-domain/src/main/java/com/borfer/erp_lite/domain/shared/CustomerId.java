package com.borfer.erp_lite.domain.shared;

import java.util.Objects;

public record CustomerId(Long value) {

    public CustomerId {
        Objects.requireNonNull(value, "CustomerId cannot be null");
        if (value <= 0) {
            throw new IllegalArgumentException("CustomerId must be greater than 0, got: " + value);
        }
    }

    public static CustomerId of(Long value) {
        return new CustomerId(value);
    }
}