package com.borfer.erp_lite.order;

import com.borfer.erp_lite.domain.order.OrderId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("OrderId Value Object Test")
public class OrderIdTest {

    @Test
    @DisplayName("Should throw NullPointerException when value is null")
    void should_throw_NullPointerException_when_value_is_null() {
        final String message = "OrderId cannot be null";

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            new OrderId(null);
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should create OrderId successfully when value is valid")
    void should_create_OrderId_successfully_when_value_is_valid() {
        final UUID uuid = UUID.randomUUID();

        OrderId orderId = new OrderId(uuid);

        assertEquals(orderId.value(), uuid);
    }

    @Test
    @DisplayName("Should create OrderId using factory method of")
    void should_create_OrderId_using_factory_method_of() {
        final UUID uuid = UUID.randomUUID();

        OrderId orderId = OrderId.of(uuid);

        assertEquals(orderId.value(), uuid);
    }

    @Test
    @DisplayName("Should generate a non-null OrderId using generate")
    void should_generate_a_non_null_OrderId_using_generate() {
        OrderId orderId = OrderId.generate();

        assertNotNull(orderId.value());
    }

    @Test
    @DisplayName("Should generate unique OrderIds on each call")
    void should_generate_unique_OrderIds_on_each_call() {
        OrderId first = OrderId.generate();
        OrderId second = OrderId.generate();

        assertNotEquals(first, second);
    }
}
