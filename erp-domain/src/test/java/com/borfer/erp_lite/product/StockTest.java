package com.borfer.erp_lite.product;

import com.borfer.erp_lite.domain.product.Stock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Stock Value Object Test")
public class StockTest {

    @Test
    @DisplayName("Should throw NullPointerException when value is null")
    void should_throw_NullPointerException_when_value_is_null() {
        final String message = "Stock cannot be null";

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            new Stock(null);
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when value is negative")
    void should_throw_IllegalArgumentException_when_value_is_negative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Stock(-1);
        });

        assertTrue(exception.getMessage().contains("-1"));
    }

    @Test
    @DisplayName("Should create Stock successfully when value is zero")
    void should_create_Stock_successfully_when_value_is_zero() {
        Stock stock = new Stock(0);

        assertEquals(stock.value(), 0);
    }

    @Test
    @DisplayName("Should create Stock successfully when value is positive")
    void should_create_Stock_successfully_when_value_is_positive() {
        Stock stock = new Stock(10);

        assertEquals(stock.value(), 10);
    }

    @Test
    @DisplayName("Should create Stock using factory method of")
    void should_create_Stock_using_factory_method_of() {
        Stock stock = Stock.of(5);

        assertEquals(stock.value(), 5);
    }

    @Test
    @DisplayName("Should create Stock with zero value using factory method zero")
    void should_create_Stock_with_zero_value_using_factory_method_zero() {
        Stock stock = Stock.zero();

        assertEquals(stock.value(), 0);
    }

    @Test
    @DisplayName("Should increment Stock by a positive quantity")
    void should_increment_Stock_by_a_positive_quantity() {
        Stock stock = Stock.of(10);

        Stock result = stock.increment(5);

        assertEquals(result.value(), 15);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when incrementing by zero")
    void should_throw_IllegalArgumentException_when_incrementing_by_zero() {
        final String message = "Increment quantity must be positive";
        Stock stock = Stock.of(10);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            stock.increment(0);
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when incrementing by negative quantity")
    void should_throw_IllegalArgumentException_when_incrementing_by_negative_quantity() {
        Stock stock = Stock.of(10);

        assertThrows(IllegalArgumentException.class, () -> {
            stock.increment(-1);
        });
    }

    @Test
    @DisplayName("Should decrement Stock by a positive quantity when sufficient stock available")
    void should_decrement_Stock_when_sufficient_stock_available() {
        Stock stock = Stock.of(10);

        Stock result = stock.decrement(3);

        assertEquals(result.value(), 7);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when decrementing by zero")
    void should_throw_IllegalArgumentException_when_decrementing_by_zero() {
        final String message = "Decrement quantity must be positive";
        Stock stock = Stock.of(10);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            stock.decrement(0);
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when decrementing more than available")
    void should_throw_IllegalArgumentException_when_decrementing_more_than_available() {
        Stock stock = Stock.of(5);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            stock.decrement(10);
        });

        assertTrue(exception.getMessage().contains("available=5"));
        assertTrue(exception.getMessage().contains("requested=10"));
    }

    @Test
    @DisplayName("Should return true when required quantity is available")
    void should_return_true_when_required_quantity_is_available() {
        Stock stock = Stock.of(10);

        assertTrue(stock.hasAvailable(10));
    }

    @Test
    @DisplayName("Should return false when required quantity exceeds available stock")
    void should_return_false_when_required_quantity_exceeds_available_stock() {
        Stock stock = Stock.of(5);

        assertFalse(stock.hasAvailable(6));
    }
}
