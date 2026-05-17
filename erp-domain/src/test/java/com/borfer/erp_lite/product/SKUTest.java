package com.borfer.erp_lite.product;

import com.borfer.erp_lite.domain.product.SKU;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SKU Value Object Test")
public class SKUTest {

    @Test
    @DisplayName("Should throw NullPointerException when value is null")
    void should_throw_NullPointerException_when_value_is_null() {
        final String message = "SKU cannot be null";

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            new SKU(null);
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when SKU format is invalid lowercase")
    void should_throw_IllegalArgumentException_when_SKU_format_is_invalid_lowercase() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new SKU("abc-001");
        });

        assertTrue(exception.getMessage().contains("abc-001"));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when SKU has no dash separator")
    void should_throw_IllegalArgumentException_when_SKU_has_no_dash_separator() {
        assertThrows(IllegalArgumentException.class, () -> {
            new SKU("PROD001");
        });
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when SKU has wrong digit count")
    void should_throw_IllegalArgumentException_when_SKU_has_wrong_digit_count() {
        assertThrows(IllegalArgumentException.class, () -> {
            new SKU("PROD-01");
        });
    }

    @Test
    @DisplayName("Should create SKU successfully with valid format")
    void should_create_SKU_successfully_with_valid_format() {
        final String value = "PROD-001";

        SKU sku = new SKU(value);

        assertEquals(sku.value(), value);
    }

    @Test
    @DisplayName("Should create SKU using factory method of")
    void should_create_SKU_using_factory_method_of() {
        SKU sku = SKU.of("ITEM-999");

        assertEquals(sku.value(), "ITEM-999");
    }
}
