package com.borfer.erp_lite.order;

import com.borfer.erp_lite.domain.order.*;
import com.borfer.erp_lite.domain.order.events.*;
import com.borfer.erp_lite.domain.product.*;
import com.borfer.erp_lite.domain.shared.CustomerId;
import com.borfer.erp_lite.domain.shared.Money;
import com.borfer.erp_lite.domain.shared.Quantity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Order Aggregate Test")
public class OrderTest {

    private static final Currency USD = Currency.getInstance("USD");
    private static final Currency EUR = Currency.getInstance("EUR");

    private Product buildProduct(String skuCode, double price, Currency currency) {
        return Product.create(
                SKU.of(skuCode),
                ProductName.of("Product " + skuCode),
                "Description",
                Money.of(price, currency),
                Stock.of(100),
                CategoryReference.of("electronics"),
                ProductImage.of("https://example.com/image.jpg"),
                "system"
        );
    }

    private Customer buildCustomer() {
        return Customer.of(CustomerId.of(1L), "John Doe");
    }

    private OrderItem buildOrderItem(double price) {
        return OrderItem.from(buildProduct("PROD-001", price, USD), Quantity.of(2));
    }

    private Order buildOrder() {
        return Order.create(
                OrderNumber.generate(),
                buildCustomer(),
                List.of(buildOrderItem(10.00)),
                "system"
        );
    }

    @Test
    @DisplayName("Should throw NullPointerException when orderNumber is null")
    void should_throw_NullPointerException_when_orderNumber_is_null() {
        final String message = "OrderNumber cannot be null";

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            Order.create(null, buildCustomer(), List.of(buildOrderItem(10.00)), "system");
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should throw NullPointerException when customer is null")
    void should_throw_NullPointerException_when_customer_is_null() {
        final String message = "Customer cannot be null";

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            Order.create(OrderNumber.generate(), null, List.of(buildOrderItem(10.00)), "system");
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should throw NullPointerException when items list is null")
    void should_throw_NullPointerException_when_items_list_is_null() {
        final String message = "Items cannot be null";

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            Order.create(OrderNumber.generate(), buildCustomer(), null, "system");
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should throw NullPointerException when createdBy is null")
    void should_throw_NullPointerException_when_createdBy_is_null() {
        final String message = "createdBy cannot be null";

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            Order.create(OrderNumber.generate(), buildCustomer(), List.of(buildOrderItem(10.00)), null);
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when items list is empty")
    void should_throw_IllegalArgumentException_when_items_list_is_empty() {
        final String message = "Order must have at least one item";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Order.create(OrderNumber.generate(), buildCustomer(), List.of(), "system");
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when items have different currencies")
    void should_throw_IllegalArgumentException_when_items_have_different_currencies() {
        OrderItem itemUSD = OrderItem.from(buildProduct("PROD-001", 10.00, USD), Quantity.of(1));
        OrderItem itemEUR = OrderItem.from(buildProduct("PROD-002", 10.00, EUR), Quantity.of(1));

        assertThrows(IllegalArgumentException.class, () -> {
            Order.create(OrderNumber.generate(), buildCustomer(), List.of(itemUSD, itemEUR), "system");
        });
    }

    @Test
    @DisplayName("Should create Order successfully with PENDING status")
    void should_create_Order_successfully_with_PENDING_status() {
        Order order = buildOrder();

        assertNotNull(order.getId());
        assertTrue(order.getStatus().isPending());
        assertEquals(order.getCustomer(), buildCustomer());
        assertNotNull(order.getTotalAmount());
        assertNotNull(order.getAuditInfo());
    }

    @Test
    @DisplayName("Should register OrderCreated event on create")
    void should_register_OrderCreated_event_on_create() {
        Order order = buildOrder();

        assertEquals(order.getDomainEvents().size(), 1);
        assertInstanceOf(OrderCreated.class, order.getDomainEvents().get(0));
    }

    @Test
    @DisplayName("Should transition to CONFIRMED and register OrderConfirmed event")
    void should_transition_to_CONFIRMED_and_register_OrderConfirmed_event() {
        Order order = buildOrder();
        order.clearDomainEvents();

        order.confirm();

        assertTrue(order.getStatus().isConfirmed());
        assertEquals(order.getDomainEvents().size(), 1);
        assertInstanceOf(OrderConfirmed.class, order.getDomainEvents().get(0));
    }

    @Test
    @DisplayName("Should throw IllegalStateException when confirming from non-PENDING status")
    void should_throw_IllegalStateException_when_confirming_from_non_PENDING_status() {
        Order order = buildOrder();
        order.confirm();

        assertThrows(IllegalStateException.class, order::confirm);
    }

    @Test
    @DisplayName("Should transition to SHIPPED and register OrderShipped event")
    void should_transition_to_SHIPPED_and_register_OrderShipped_event() {
        Order order = buildOrder();
        order.confirm();
        order.clearDomainEvents();

        order.ship();

        assertTrue(order.getStatus().isShipped());
        assertEquals(order.getDomainEvents().size(), 1);
        assertInstanceOf(OrderShipped.class, order.getDomainEvents().get(0));
    }

    @Test
    @DisplayName("Should throw IllegalStateException when shipping from non-CONFIRMED status")
    void should_throw_IllegalStateException_when_shipping_from_non_CONFIRMED_status() {
        Order order = buildOrder();

        assertThrows(IllegalStateException.class, order::ship);
    }

    @Test
    @DisplayName("Should transition to DELIVERED and register OrderDelivered event")
    void should_transition_to_DELIVERED_and_register_OrderDelivered_event() {
        Order order = buildOrder();
        order.confirm();
        order.ship();
        order.clearDomainEvents();

        order.deliver();

        assertTrue(order.getStatus().isDelivered());
        assertEquals(order.getDomainEvents().size(), 1);
        assertInstanceOf(OrderDelivered.class, order.getDomainEvents().get(0));
    }

    @Test
    @DisplayName("Should throw IllegalStateException when delivering from non-SHIPPED status")
    void should_throw_IllegalStateException_when_delivering_from_non_SHIPPED_status() {
        Order order = buildOrder();

        assertThrows(IllegalStateException.class, order::deliver);
    }

    @Test
    @DisplayName("Should cancel from PENDING and register OrderCancelled event")
    void should_cancel_from_PENDING_and_register_OrderCancelled_event() {
        Order order = buildOrder();
        order.clearDomainEvents();

        order.cancel("Customer request");

        assertTrue(order.getStatus().isCancelled());
        assertEquals(order.getDomainEvents().size(), 1);
        assertInstanceOf(OrderCancelled.class, order.getDomainEvents().get(0));
    }

    @Test
    @DisplayName("Should cancel from CONFIRMED and register OrderCancelled event")
    void should_cancel_from_CONFIRMED_and_register_OrderCancelled_event() {
        Order order = buildOrder();
        order.confirm();
        order.clearDomainEvents();

        order.cancel("Stock issue");

        assertTrue(order.getStatus().isCancelled());
        assertInstanceOf(OrderCancelled.class, order.getDomainEvents().get(0));
    }

    @Test
    @DisplayName("Should throw NullPointerException when cancelling with null reason")
    void should_throw_NullPointerException_when_cancelling_with_null_reason() {
        Order order = buildOrder();

        assertThrows(NullPointerException.class, () -> order.cancel(null));
    }

    @Test
    @DisplayName("Should throw IllegalStateException when cancelling from a final state")
    void should_throw_IllegalStateException_when_cancelling_from_a_final_state() {
        Order order = buildOrder();
        order.confirm();
        order.ship();
        order.deliver();

        assertThrows(IllegalStateException.class, () -> order.cancel("too late"));
    }

    @Test
    @DisplayName("Should add item to PENDING order and recalculate total")
    void should_add_item_to_PENDING_order_and_recalculate_total() {
        Order order = buildOrder();
        final Money totalBefore = order.getTotalAmount();
        final OrderItem newItem = buildOrderItem(5.00);

        order.addItem(newItem);

        assertEquals(order.getItems().size(), 2);
        assertNotEquals(order.getTotalAmount(), totalBefore);
    }

    @Test
    @DisplayName("Should throw NullPointerException when adding null item")
    void should_throw_NullPointerException_when_adding_null_item() {
        Order order = buildOrder();

        assertThrows(NullPointerException.class, () -> order.addItem(null));
    }

    @Test
    @DisplayName("Should throw IllegalStateException when adding item to non-PENDING order")
    void should_throw_IllegalStateException_when_adding_item_to_non_PENDING_order() {
        Order order = buildOrder();
        order.confirm();

        assertThrows(IllegalStateException.class, () -> order.addItem(buildOrderItem(5.00)));
    }

    @Test
    @DisplayName("Should remove item from PENDING order with multiple items")
    void should_remove_item_from_PENDING_order_with_multiple_items() {
        final OrderItem firstItem = buildOrderItem(10.00);
        final OrderItem secondItem = buildOrderItem(5.00);
        Order order = Order.create(OrderNumber.generate(), buildCustomer(),
                List.of(firstItem, secondItem), "system");

        order.removeItem(secondItem);

        assertEquals(order.getItems().size(), 1);
    }

    @Test
    @DisplayName("Should throw NullPointerException when removing null item")
    void should_throw_NullPointerException_when_removing_null_item() {
        Order order = buildOrder();

        assertThrows(NullPointerException.class, () -> order.removeItem(null));
    }

    @Test
    @DisplayName("Should throw IllegalStateException when removing item from non-PENDING order")
    void should_throw_IllegalStateException_when_removing_item_from_non_PENDING_order() {
        Order order = buildOrder();
        order.confirm();

        assertThrows(IllegalStateException.class, () -> order.removeItem(order.getItems().get(0)));
    }

    @Test
    @DisplayName("Should throw IllegalStateException when removing the last item from an order")
    void should_throw_IllegalStateException_when_removing_the_last_item_from_an_order() {
        final String message = "Cannot remove the last item from an order";
        Order order = buildOrder();

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            order.removeItem(order.getItems().get(0));
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should return unmodifiable list from getItems")
    void should_return_unmodifiable_list_from_getItems() {
        Order order = buildOrder();

        assertThrows(UnsupportedOperationException.class, () -> {
            order.getItems().add(buildOrderItem(5.00));
        });
    }
}
