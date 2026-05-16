package com.borfer.erp_lite.domain.order;

import com.borfer.erp_lite.domain.common.AggregateRoot;
import com.borfer.erp_lite.domain.order.events.OrderCancelled;
import com.borfer.erp_lite.domain.order.events.OrderConfirmed;
import com.borfer.erp_lite.domain.order.events.OrderCreated;
import com.borfer.erp_lite.domain.order.events.OrderDelivered;
import com.borfer.erp_lite.domain.order.events.OrderShipped;
import com.borfer.erp_lite.domain.shared.AuditInfo;
import com.borfer.erp_lite.domain.shared.Money;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
public class Order extends AggregateRoot<OrderId> {

    private OrderNumber orderNumber;
    private Customer customer;
    private OrderStatus status;
    private List<OrderItem> items;
    private Money totalAmount;
    private AuditInfo auditInfo;

    private Order(OrderId id, OrderNumber orderNumber, Customer customer,
                  List<OrderItem> items, String createdBy) {
        super(id);
        this.orderNumber = orderNumber;
        this.customer = customer;
        this.status = OrderStatus.pending();
        this.items = new ArrayList<>(items);
        this.auditInfo = AuditInfo.create(createdBy, Instant.now());
        calculateTotal();
    }

    public static Order create(OrderNumber orderNumber, Customer customer,
                               List<OrderItem> items, String createdBy) {
        Objects.requireNonNull(orderNumber, "OrderNumber cannot be null");
        Objects.requireNonNull(customer, "Customer cannot be null");
        Objects.requireNonNull(items, "Items cannot be null");
        Objects.requireNonNull(createdBy, "createdBy cannot be null");

        if (items.isEmpty()) throw new IllegalArgumentException("Order must have at least one item");

        OrderId id = OrderId.generate();
        Order order = new Order(id, orderNumber, customer, items, createdBy);
        validateItems(items);
        order.registerEvent(new OrderCreated(
                id,
                customer.customerId(),
                customer.customerName(),
                order.totalAmount,
                Instant.now()
        ));
        return order;
    }

    public void confirm() {
        validateTransition(OrderStatus.confirmed());
        this.status = OrderStatus.confirmed();
        this.auditInfo = this.auditInfo.updateTimestamp();
        registerEvent(new OrderConfirmed(this.id, Instant.now()));
    }

    public void ship() {
        validateTransition(OrderStatus.shipped());
        this.status = OrderStatus.shipped();
        this.auditInfo = this.auditInfo.updateTimestamp();
        registerEvent(new OrderShipped(this.id, Instant.now()));
    }

    public void deliver() {
        validateTransition(OrderStatus.delivered());
        this.status = OrderStatus.delivered();
        this.auditInfo = this.auditInfo.updateTimestamp();
        registerEvent(new OrderDelivered(this.id, Instant.now()));
    }

    public void cancel(String reason) {
        Objects.requireNonNull(reason, "Cancellation reason cannot be null");
        validateTransition(OrderStatus.cancelled());
        this.status = OrderStatus.cancelled();
        this.auditInfo = this.auditInfo.updateTimestamp();
        registerEvent(new OrderCancelled(this.id, reason, Instant.now()));
    }

    public void addItem(OrderItem item) {
        Objects.requireNonNull(item, "Item cannot be null");
        if (!this.status.isPending()) {
            throw new IllegalStateException(
                    "Cannot add items to order in status: " + this.status.value());
        }
        this.items.add(item);
        calculateTotal();
        this.auditInfo = this.auditInfo.updateTimestamp();
    }

    public void removeItem(OrderItem item) {
        Objects.requireNonNull(item, "Item cannot be null");
        if (!this.status.isPending()) {
            throw new IllegalStateException(
                    "Cannot remove items from order in status: " + this.status.value());
        }
        if (this.items.size() <= 1) {
            throw new IllegalStateException("Cannot remove the last item from an order");
        }
        this.items.remove(item);
        calculateTotal();
        this.auditInfo = this.auditInfo.updateTimestamp();
    }

    public void calculateTotal() {
        if (items == null || items.isEmpty()) return;
        this.totalAmount = items.stream()
                .map(OrderItem::getSubtotal)
                .reduce(Money::add)
                .orElseThrow(() -> new IllegalStateException("Cannot calculate total for empty order"));
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    private static void validateItems(List<OrderItem> items) {
        if (items.isEmpty()) {
            throw new IllegalArgumentException("Order items cannot be empty");
        }

        java.util.Currency firstCurrency = items.get(0).getUnitPrice().currency();

        items.forEach(item -> {
            if (!item.getUnitPrice().currency().equals(firstCurrency)) {
                throw new IllegalArgumentException(
                        "All items must have the same currency. Expected: " + firstCurrency +
                                ", found: " + item.getUnitPrice().currency()
                );
            }

            // Validate subtotal calculation
            Money calculatedSubtotal = item.calculateSubtotal();
            if (!item.getSubtotal().equals(calculatedSubtotal)) {
                throw new IllegalArgumentException(
                        "Item subtotal mismatch. Expected: " + calculatedSubtotal +
                                ", found: " + item.getSubtotal()
                );
            }

        });
    }

    private void validateTransition(OrderStatus nextStatus) {
        if (!this.status.canTransitionTo(nextStatus)) {
            throw new IllegalStateException(
                    "Cannot transition from " + this.status.value() + " to " + nextStatus.value());
        }
    }
}
