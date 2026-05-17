package com.borfer.erp_lite.order;

import com.borfer.erp_lite.domain.order.OrderItem;
import com.borfer.erp_lite.domain.product.*;
import com.borfer.erp_lite.domain.shared.Money;
import com.borfer.erp_lite.domain.shared.Quantity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("OrderItem Entity Test")
public class OrderItemTest {

    private static final Currency USD = Currency.getInstance("USD");

    private Product buildProduct() {
        return Product.create(
                SKU.of("PROD-001"),
                ProductName.of("Test Product"),
                "A test description",
                Money.of(15.00, USD),
                Stock.of(50),
                CategoryReference.of("electronics"),
                ProductImage.of("https://example.com/image.jpg"),
                "system"
        );
    }

    @Test
    @DisplayName("Should throw NullPointerException when product is null")
    void should_throw_NullPointerException_when_product_is_null() {
        final String message = "Product cannot be null";

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            OrderItem.from(null, Quantity.of(2));
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should throw NullPointerException when quantity is null")
    void should_throw_NullPointerException_when_quantity_is_null() {
        final String message = "Quantity cannot be null";

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            OrderItem.from(buildProduct(), null);
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should create OrderItem as a snapshot of the product with correct fields")
    void should_create_OrderItem_as_snapshot_of_product_with_correct_fields() {
        final Product product = buildProduct();
        final Quantity quantity = Quantity.of(3);

        OrderItem item = OrderItem.from(product, quantity);

        assertNotNull(item.getId());
        assertEquals(item.getProductReference(), product.getId());
        assertEquals(item.getProductName(), product.getName().value());
        assertEquals(item.getQuantity(), quantity);
        assertEquals(item.getUnitPrice(), product.getPrice());
    }

    @Test
    @DisplayName("Should calculate subtotal as unitPrice multiplied by quantity")
    void should_calculate_subtotal_as_unitPrice_multiplied_by_quantity() {
        final Product product = buildProduct();
        final Quantity quantity = Quantity.of(4);

        OrderItem item = OrderItem.from(product, quantity);

        assertEquals(item.calculateSubtotal(), product.getPrice().multiply(quantity));
    }

    @Test
    @DisplayName("Should store subtotal equal to calculated subtotal on creation")
    void should_store_subtotal_equal_to_calculated_subtotal_on_creation() {
        final Product product = buildProduct();
        final Quantity quantity = Quantity.of(2);

        OrderItem item = OrderItem.from(product, quantity);

        assertEquals(item.getSubtotal(), item.calculateSubtotal());
    }
}
