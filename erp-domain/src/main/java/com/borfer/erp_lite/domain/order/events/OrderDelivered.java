package com.borfer.erp_lite.domain.order.events;

import com.borfer.erp_lite.domain.common.DomainEvent;
import com.borfer.erp_lite.domain.order.OrderId;

import java.time.Instant;

public record OrderDelivered(
        OrderId orderId,
        Instant timestamp
) implements DomainEvent {
}
