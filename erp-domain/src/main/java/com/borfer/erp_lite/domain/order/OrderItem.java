package com.borfer.erp_lite.domain.order;

import com.borfer.erp_lite.domain.common.Entity;
import com.borfer.erp_lite.domain.product.Product;
import com.borfer.erp_lite.domain.product.ProductId;
import com.borfer.erp_lite.domain.shared.Money;
import com.borfer.erp_lite.domain.shared.Quantity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
public class OrderItem extends Entity<OrderItemId> {

    private ProductId productReference;
    private String productName;
    private Quantity quantity;
    private Money unitPrice;
    private Money subtotal;

    public static OrderItem from(Product product, Quantity quantity) {
        Objects.requireNonNull(product, "Product cannot be null");
        Objects.requireNonNull(quantity, "Quantity cannot be null");

        Money subtotal = product.getPrice().multiply(quantity);
        return new OrderItem(
                OrderItemId.generate(),
                product.getId(),
                product.getName().value(),
                quantity,
                product.getPrice(),
                subtotal
        );
    }


    private OrderItem(
            OrderItemId id,
            ProductId productReference,
            String productName,
            Quantity quantity,
            Money unitPrice,
            Money subtotal
    ) {
        super(id);
        this.productReference = productReference;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = subtotal;
    }

    public Money calculateSubtotal() {
        return unitPrice.multiply(quantity);
    }
}
