package com.borfer.erp_lite.domain.order;

import java.time.Year;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public record OrderNumber(String value) {

    private static final Pattern ORDER_NUMBER_PATTERN = Pattern.compile("^ORD-\\d{4}-\\d{3}$");
    private static final AtomicInteger counter = new AtomicInteger(0);

    public OrderNumber {
        Objects.requireNonNull(value, "OrderNumber cannot be null");
        if (!ORDER_NUMBER_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException(
                    "Invalid OrderNumber format. Expected ORD-YYYY-NNN, got: " + value);
        }
    }

    public static OrderNumber of(String value) {
        return new OrderNumber(value);
    }

    public static OrderNumber generate() {
        int year = Year.now().getValue();
        int seq = counter.incrementAndGet() % 1000;
        if (seq == 0) seq = 1;
        return new OrderNumber(String.format("ORD-%d-%03d", year, seq));
    }
}
