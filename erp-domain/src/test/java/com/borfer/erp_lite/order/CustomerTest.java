package com.borfer.erp_lite.order;

import com.borfer.erp_lite.domain.order.Customer;
import com.borfer.erp_lite.domain.shared.CustomerId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Customer Value Object Test")
public class CustomerTest {

    @Test
    @DisplayName("Should throw NullPointerException when customerId is null")
    void should_throw_NullPointerException_when_customerId_is_null() {
        final String message = "CustomerId cannot be null";

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            new Customer(null, "John Doe");
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should throw NullPointerException when customerName is null")
    void should_throw_NullPointerException_when_customerName_is_null() {
        final String message = "CustomerName cannot be null";

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            new Customer(CustomerId.of(1L), null);
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when customerName is blank")
    void should_throw_IllegalArgumentException_when_customerName_is_blank() {
        final String message = "CustomerName cannot be blank";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Customer(CustomerId.of(1L), "  ");
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should create Customer successfully with valid parameters")
    void should_create_Customer_successfully_with_valid_parameters() {
        final CustomerId customerId = CustomerId.of(1L);
        final String customerName = "John Doe";

        Customer customer = new Customer(customerId, customerName);

        assertEquals(customer.customerId(), customerId);
        assertEquals(customer.customerName(), customerName);
    }

    @Test
    @DisplayName("Should create Customer using factory method of")
    void should_create_Customer_using_factory_method_of() {
        Customer customer = Customer.of(CustomerId.of(2L), "Jane Smith");

        assertEquals(customer.customerName(), "Jane Smith");
    }
}
