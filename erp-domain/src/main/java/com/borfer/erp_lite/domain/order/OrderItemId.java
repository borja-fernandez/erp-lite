package com.borfer.erp_lite.domain.order;

import java.util.Objects;
import java.util.UUID;

public record OrderItemId(UUID value) {

    public OrderItemId {
        Objects.requireNonNull(value, "OrderItemId cannot be null");
    }

    public static OrderItemId of(UUID value) {
        return new OrderItemId(value);
    }

    public static OrderItemId generate() {
        return new OrderItemId(UUID.randomUUID());
    }
}
