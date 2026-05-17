package com.borfer.erp_lite.shared;

import com.borfer.erp_lite.domain.shared.Quantity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Quantity Value Object Test")
public class QuantityTest {

    @Test
    @DisplayName("Should throw NullPointerException when value is null")
    void should_throw_NullPointerException_when_value_is_null() {
        final String message = "Quantity cannot be null";

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            new Quantity(null);
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when value is zero")
    void should_throw_IllegalArgumentException_when_value_is_zero() {
        final String message = "Quantity must be greater than 0, got: 0";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Quantity(0);
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when value is negative")
    void should_throw_IllegalArgumentException_when_value_is_negative() {
        final String message = "Quantity must be greater than 0, got: -1";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Quantity(-1);
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should create Quantity successfully when value is positive")
    void should_create_Quantity_successfully_when_value_is_positive() {
        Quantity quantity = new Quantity(5);

        assertEquals(quantity.value(), 5);
    }

    @Test
    @DisplayName("Should create Quantity using factory method of")
    void should_create_Quantity_using_factory_method_of() {
        Quantity quantity = Quantity.of(3);

        assertEquals(quantity.value(), 3);
    }
}