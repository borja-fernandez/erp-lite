package com.borfer.erp_lite.domain.product;

import java.util.Objects;
import java.util.regex.Pattern;

public record SKU(String value) {

    private static final Pattern SKU_PATTERN = Pattern.compile("^[A-Z]+-\\d{3}$");

    public SKU {
        Objects.requireNonNull(value, "SKU cannot be null");
        if (!SKU_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid SKU format. Expected [A-Z]+-NNN, got: " + value);
        }
    }

    public static SKU of(String value) {
        return new SKU(value);
    }
}