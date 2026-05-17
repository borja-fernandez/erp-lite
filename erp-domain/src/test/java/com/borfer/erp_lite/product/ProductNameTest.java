package com.borfer.erp_lite.product;

import com.borfer.erp_lite.domain.product.ProductName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ProductName Value Object Test")
public class ProductNameTest {

    @Test
    @DisplayName("Should throw NullPointerException when value is null")
    void should_throw_NullPointerException_when_value_is_null() {
        final String message = "ProductName cannot be null";

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            new ProductName(null);
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when value is too short")
    void should_throw_IllegalArgumentException_when_value_is_too_short() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new ProductName("AB");
        });

        assertTrue(exception.getMessage().contains("2"));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when value is too long")
    void should_throw_IllegalArgumentException_when_value_is_too_long() {
        final String tooLong = "A".repeat(201);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new ProductName(tooLong);
        });

        assertTrue(exception.getMessage().contains("201"));
    }

    @Test
    @DisplayName("Should create ProductName successfully with minimum length")
    void should_create_ProductName_successfully_with_minimum_length() {
        ProductName name = new ProductName("ABC");

        assertEquals(name.value(), "ABC");
    }

    @Test
    @DisplayName("Should create ProductName successfully with maximum length")
    void should_create_ProductName_successfully_with_maximum_length() {
        final String maxLength = "A".repeat(200);

        ProductName name = new ProductName(maxLength);

        assertEquals(name.value(), maxLength);
    }

    @Test
    @DisplayName("Should create ProductName using factory method of")
    void should_create_ProductName_using_factory_method_of() {
        ProductName name = ProductName.of("Valid Product Name");

        assertEquals(name.value(), "Valid Product Name");
    }
}
