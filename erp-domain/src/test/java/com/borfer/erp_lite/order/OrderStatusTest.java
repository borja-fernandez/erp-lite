package com.borfer.erp_lite.order;

import com.borfer.erp_lite.domain.order.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("OrderStatus Value Object Test")
public class OrderStatusTest {

    @Test
    @DisplayName("Should throw NullPointerException when value is null")
    void should_throw_NullPointerException_when_value_is_null() {
        final String message = "OrderStatus cannot be null";

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            new OrderStatus(null);
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when value is invalid")
    void should_throw_IllegalArgumentException_when_value_is_invalid() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new OrderStatus("INVALID_STATUS");
        });

        assertTrue(exception.getMessage().contains("INVALID_STATUS"));
    }

    @Test
    @DisplayName("Should create all valid statuses using factory methods")
    void should_create_all_valid_statuses_using_factory_methods() {
        assertEquals(OrderStatus.pending().value(), "PENDING");
        assertEquals(OrderStatus.confirmed().value(), "CONFIRMED");
        assertEquals(OrderStatus.shipped().value(), "SHIPPED");
        assertEquals(OrderStatus.delivered().value(), "DELIVERED");
        assertEquals(OrderStatus.cancelled().value(), "CANCELLED");
    }

    @Test
    @DisplayName("Should create OrderStatus using factory method of")
    void should_create_OrderStatus_using_factory_method_of() {
        OrderStatus status = OrderStatus.of("PENDING");

        assertEquals(status.value(), "PENDING");
    }

    @Test
    @DisplayName("Should return correct boolean from predicate methods")
    void should_return_correct_boolean_from_predicate_methods() {
        assertTrue(OrderStatus.pending().isPending());
        assertTrue(OrderStatus.confirmed().isConfirmed());
        assertTrue(OrderStatus.shipped().isShipped());
        assertTrue(OrderStatus.delivered().isDelivered());
        assertTrue(OrderStatus.cancelled().isCancelled());

        assertFalse(OrderStatus.pending().isConfirmed());
        assertFalse(OrderStatus.confirmed().isPending());
    }

    @Test
    @DisplayName("Should return true for isFinalState when DELIVERED or CANCELLED")
    void should_return_true_for_isFinalState_when_DELIVERED_or_CANCELLED() {
        assertTrue(OrderStatus.delivered().isFinalState());
        assertTrue(OrderStatus.cancelled().isFinalState());
    }

    @Test
    @DisplayName("Should return false for isFinalState when not a final state")
    void should_return_false_for_isFinalState_when_not_a_final_state() {
        assertFalse(OrderStatus.pending().isFinalState());
        assertFalse(OrderStatus.confirmed().isFinalState());
        assertFalse(OrderStatus.shipped().isFinalState());
    }

    @Test
    @DisplayName("Should allow valid transitions from PENDING")
    void should_allow_valid_transitions_from_PENDING() {
        OrderStatus pending = OrderStatus.pending();

        assertTrue(pending.canTransitionTo(OrderStatus.confirmed()));
        assertTrue(pending.canTransitionTo(OrderStatus.cancelled()));
    }

    @Test
    @DisplayName("Should reject invalid transitions from PENDING")
    void should_reject_invalid_transitions_from_PENDING() {
        OrderStatus pending = OrderStatus.pending();

        assertFalse(pending.canTransitionTo(OrderStatus.shipped()));
        assertFalse(pending.canTransitionTo(OrderStatus.delivered()));
    }

    @Test
    @DisplayName("Should allow valid transitions from CONFIRMED")
    void should_allow_valid_transitions_from_CONFIRMED() {
        OrderStatus confirmed = OrderStatus.confirmed();

        assertTrue(confirmed.canTransitionTo(OrderStatus.shipped()));
        assertTrue(confirmed.canTransitionTo(OrderStatus.cancelled()));
    }

    @Test
    @DisplayName("Should reject invalid transitions from CONFIRMED")
    void should_reject_invalid_transitions_from_CONFIRMED() {
        OrderStatus confirmed = OrderStatus.confirmed();

        assertFalse(confirmed.canTransitionTo(OrderStatus.delivered()));
        assertFalse(confirmed.canTransitionTo(OrderStatus.pending()));
    }

    @Test
    @DisplayName("Should allow valid transition from SHIPPED to DELIVERED")
    void should_allow_valid_transition_from_SHIPPED_to_DELIVERED() {
        OrderStatus shipped = OrderStatus.shipped();

        assertTrue(shipped.canTransitionTo(OrderStatus.delivered()));
    }

    @Test
    @DisplayName("Should reject invalid transitions from SHIPPED")
    void should_reject_invalid_transitions_from_SHIPPED() {
        OrderStatus shipped = OrderStatus.shipped();

        assertFalse(shipped.canTransitionTo(OrderStatus.cancelled()));
        assertFalse(shipped.canTransitionTo(OrderStatus.pending()));
    }

    @Test
    @DisplayName("Should reject all transitions from final states DELIVERED and CANCELLED")
    void should_reject_all_transitions_from_final_states() {
        assertFalse(OrderStatus.delivered().canTransitionTo(OrderStatus.pending()));
        assertFalse(OrderStatus.cancelled().canTransitionTo(OrderStatus.pending()));
    }
}
