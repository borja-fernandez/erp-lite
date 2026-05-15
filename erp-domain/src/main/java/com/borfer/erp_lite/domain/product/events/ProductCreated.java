package com.borfer.erp_lite.domain.product.events;

import com.borfer.erp_lite.domain.common.DomainEvent;
import com.borfer.erp_lite.domain.product.ProductId;
import com.borfer.erp_lite.domain.product.ProductName;
import com.borfer.erp_lite.domain.product.SKU;
import com.borfer.erp_lite.domain.shared.Money;

import java.time.Instant;

public record ProductCreated(
        ProductId productId,
        SKU sku,
        ProductName name,
        Money price,
        Instant timestamp
) implements DomainEvent {
}
