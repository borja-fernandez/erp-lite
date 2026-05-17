package com.borfer.erp_lite.order;

import com.borfer.erp_lite.domain.order.OrderItemId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("OrderItemId Value Object Test")
public class OrderItemIdTest {

    @Test
    @DisplayName("Should throw NullPointerException when value is null")
    void should_throw_NullPointerException_when_value_is_null() {
        final String message = "OrderItemId cannot be null";

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            new OrderItemId(null);
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should create OrderItemId successfully when value is valid")
    void should_create_OrderItemId_successfully_when_value_is_valid() {
        final UUID uuid = UUID.randomUUID();

        OrderItemId orderItemId = new OrderItemId(uuid);

        assertEquals(orderItemId.value(), uuid);
    }

    @Test
    @DisplayName("Should create OrderItemId using factory method of")
    void should_create_OrderItemId_using_factory_method_of() {
        final UUID uuid = UUID.randomUUID();

        OrderItemId orderItemId = OrderItemId.of(uuid);

        assertEquals(orderItemId.value(), uuid);
    }

    @Test
    @DisplayName("Should generate a non-null OrderItemId using generate")
    void should_generate_a_non_null_OrderItemId_using_generate() {
        OrderItemId orderItemId = OrderItemId.generate();

        assertNotNull(orderItemId.value());
    }

    @Test
    @DisplayName("Should generate unique OrderItemIds on each call")
    void should_generate_unique_OrderItemIds_on_each_call() {
        OrderItemId first = OrderItemId.generate();
        OrderItemId second = OrderItemId.generate();

        assertNotEquals(first, second);
    }
}
