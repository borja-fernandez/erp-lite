package com.borfer.erp_lite.order;

import com.borfer.erp_lite.domain.order.OrderNumber;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("OrderNumber Value Object Test")
public class OrderNumberTest {

    private static final Pattern ORDER_NUMBER_PATTERN = Pattern.compile("^ORD-\\d{4}-\\d{3}$");

    @Test
    @DisplayName("Should throw NullPointerException when value is null")
    void should_throw_NullPointerException_when_value_is_null() {
        final String message = "OrderNumber cannot be null";

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            new OrderNumber(null);
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when format is invalid")
    void should_throw_IllegalArgumentException_when_format_is_invalid() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new OrderNumber("INVALID-FORMAT");
        });

        assertTrue(exception.getMessage().contains("INVALID-FORMAT"));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when year digits are missing")
    void should_throw_IllegalArgumentException_when_year_digits_are_missing() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OrderNumber("ORD-26-001");
        });
    }

    @Test
    @DisplayName("Should create OrderNumber successfully with valid format")
    void should_create_OrderNumber_successfully_with_valid_format() {
        final String value = "ORD-2026-001";

        OrderNumber orderNumber = new OrderNumber(value);

        assertEquals(orderNumber.value(), value);
    }

    @Test
    @DisplayName("Should create OrderNumber using factory method of")
    void should_create_OrderNumber_using_factory_method_of() {
        OrderNumber orderNumber = OrderNumber.of("ORD-2026-042");

        assertEquals(orderNumber.value(), "ORD-2026-042");
    }

    @Test
    @DisplayName("Should generate OrderNumber matching the expected pattern")
    void should_generate_OrderNumber_matching_the_expected_pattern() {
        OrderNumber orderNumber = OrderNumber.generate();

        assertTrue(ORDER_NUMBER_PATTERN.matcher(orderNumber.value()).matches());
    }

    @Test
    @DisplayName("Should generate unique OrderNumbers on each call")
    void should_generate_unique_OrderNumbers_on_each_call() {
        OrderNumber first = OrderNumber.generate();
        OrderNumber second = OrderNumber.generate();

        assertNotEquals(first.value(), second.value());
    }
}
