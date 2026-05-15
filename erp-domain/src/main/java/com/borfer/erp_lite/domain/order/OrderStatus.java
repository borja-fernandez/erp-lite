package com.borfer.erp_lite.domain.order;

import java.util.Objects;
import java.util.Set;

public record OrderStatus(String value) {

    private static final Set<String> VALID_STATUSES =
            Set.of("PENDING", "CONFIRMED", "SHIPPED", "DELIVERED", "CANCELLED");

    public OrderStatus {
        Objects.requireNonNull(value, "OrderStatus cannot be null");
        if (!VALID_STATUSES.contains(value)) {
            throw new IllegalArgumentException(
                    "Invalid OrderStatus: " + value + ". Valid values: " + VALID_STATUSES);
        }
    }

    public static OrderStatus of(String value) { return new OrderStatus(value); }

    public static OrderStatus pending()   { return new OrderStatus("PENDING"); }
    public static OrderStatus confirmed() { return new OrderStatus("CONFIRMED"); }
    public static OrderStatus shipped()   { return new OrderStatus("SHIPPED"); }
    public static OrderStatus delivered() { return new OrderStatus("DELIVERED"); }
    public static OrderStatus cancelled() { return new OrderStatus("CANCELLED"); }

    public boolean canTransitionTo(OrderStatus nextStatus) {
        return switch (this.value) {
            case "PENDING"   -> nextStatus.isConfirmed() || nextStatus.isCancelled();
            case "CONFIRMED" -> nextStatus.isShipped()   || nextStatus.isCancelled();
            case "SHIPPED"   -> nextStatus.isDelivered();
            default          -> false;
        };
    }

    public boolean isPending()   { return "PENDING".equals(this.value); }
    public boolean isConfirmed() { return "CONFIRMED".equals(this.value); }
    public boolean isShipped()   { return "SHIPPED".equals(this.value); }
    public boolean isDelivered() { return "DELIVERED".equals(this.value); }
    public boolean isCancelled() { return "CANCELLED".equals(this.value); }
    public boolean isFinalState() { return isDelivered() || isCancelled(); }
}
