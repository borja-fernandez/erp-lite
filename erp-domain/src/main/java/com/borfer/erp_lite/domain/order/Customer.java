package com.borfer.erp_lite.domain.order;

import com.borfer.erp_lite.domain.shared.CustomerId;

import java.util.Objects;

public record Customer(CustomerId customerId, String customerName) {

    public Customer {
        Objects.requireNonNull(customerId, "CustomerId cannot be null");
        Objects.requireNonNull(customerName, "CustomerName cannot be null");
        if (customerName.isBlank()) throw new IllegalArgumentException("CustomerName cannot be blank");
    }

    public static Customer of(CustomerId customerId, String customerName) {
        return new Customer(customerId, customerName);
    }
}
