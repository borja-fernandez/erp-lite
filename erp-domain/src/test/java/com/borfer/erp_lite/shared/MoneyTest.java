package com.borfer.erp_lite.shared;

import com.borfer.erp_lite.domain.shared.Money;
import com.borfer.erp_lite.domain.shared.Quantity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Money Value Object Test")
public class MoneyTest {

    private static final Currency USD = Currency.getInstance("USD");
    private static final Currency EUR = Currency.getInstance("EUR");

    @Test
    @DisplayName("Should throw NullPointerException when amount is null")
    void should_throw_NullPointerException_when_amount_is_null() {
        final String message = "Amount cannot be null";

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            new Money(null, USD);
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should throw NullPointerException when currency is null")
    void should_throw_NullPointerException_when_currency_is_null() {
        final String message = "Currency cannot be null";

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            new Money(BigDecimal.TEN, null);
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when amount is negative")
    void should_throw_IllegalArgumentException_when_amount_is_negative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Money(BigDecimal.valueOf(-1), USD);
        });

        assertTrue(exception.getMessage().contains("-1"));
    }

    @Test
    @DisplayName("Should create Money successfully with valid BigDecimal amount")
    void should_create_Money_successfully_with_valid_BigDecimal_amount() {
        Money money = Money.of(BigDecimal.valueOf(10), USD);

        assertEquals(money.currency(), USD);
        assertEquals(money.amount(), BigDecimal.valueOf(10).setScale(2));
    }

    @Test
    @DisplayName("Should create Money successfully with double amount")
    void should_create_Money_successfully_with_double_amount() {
        Money money = Money.of(9.99, USD);

        assertEquals(money.amount(), BigDecimal.valueOf(9.99).setScale(2));
    }

    @Test
    @DisplayName("Should add two Money values with the same currency")
    void should_add_two_Money_values_with_the_same_currency() {
        Money ten = Money.of(10.00, USD);
        Money five = Money.of(5.00, USD);

        Money result = ten.add(five);

        assertEquals(result.amount(), BigDecimal.valueOf(15.00).setScale(2));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when adding Money with different currencies")
    void should_throw_IllegalArgumentException_when_adding_Money_with_different_currencies() {
        Money usd = Money.of(10.00, USD);
        Money eur = Money.of(10.00, EUR);

        assertThrows(IllegalArgumentException.class, () -> {
            usd.add(eur);
        });
    }

    @Test
    @DisplayName("Should subtract two Money values with the same currency")
    void should_subtract_two_Money_values_with_the_same_currency() {
        Money ten = Money.of(10.00, USD);
        Money three = Money.of(3.00, USD);

        Money result = ten.subtract(three);

        assertEquals(result.amount(), BigDecimal.valueOf(7.00).setScale(2));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when subtracting Money with different currencies")
    void should_throw_IllegalArgumentException_when_subtracting_Money_with_different_currencies() {
        Money usd = Money.of(10.00, USD);
        Money eur = Money.of(5.00, EUR);

        assertThrows(IllegalArgumentException.class, () -> {
            usd.subtract(eur);
        });
    }

    @Test
    @DisplayName("Should multiply Money by a positive integer")
    void should_multiply_Money_by_a_positive_integer() {
        Money money = Money.of(5.00, USD);

        Money result = money.multiply(3);

        assertEquals(result.amount(), BigDecimal.valueOf(15.00).setScale(2));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when multiplying Money by a negative integer")
    void should_throw_IllegalArgumentException_when_multiplying_Money_by_negative_integer() {
        final String message = "Multiplier cannot be negative";
        Money money = Money.of(5.00, USD);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            money.multiply(-1);
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should multiply Money by a Quantity")
    void should_multiply_Money_by_a_Quantity() {
        Money money = Money.of(4.00, USD);
        Quantity quantity = Quantity.of(3);

        Money result = money.multiply(quantity);

        assertEquals(result.amount(), BigDecimal.valueOf(12.00).setScale(2));
    }
}
