package com.borfer.erp_lite.shared;

import com.borfer.erp_lite.domain.shared.CustomerId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CustomerId Value Object Test")
public class CustomerIdTest {

    @Test
    @DisplayName("Should throw NullPointerException when value is null")
    void should_throw_NullPointerException_when_value_is_null() {
        final String message = "CustomerId cannot be null";

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            new CustomerId(null);
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when value is zero")
    void should_throw_IllegalArgumentException_when_value_is_zero() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new CustomerId(0L);
        });

        assertTrue(exception.getMessage().contains("0"));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when value is negative")
    void should_throw_IllegalArgumentException_when_value_is_negative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new CustomerId(-1L);
        });

        assertTrue(exception.getMessage().contains("-1"));
    }

    @Test
    @DisplayName("Should create CustomerId successfully when value is positive")
    void should_create_CustomerId_successfully_when_value_is_positive() {
        CustomerId customerId = new CustomerId(1L);

        assertEquals(customerId.value(), 1L);
    }

    @Test
    @DisplayName("Should create CustomerId using factory method of")
    void should_create_CustomerId_using_factory_method_of() {
        CustomerId customerId = CustomerId.of(42L);

        assertEquals(customerId.value(), 42L);
    }
}
