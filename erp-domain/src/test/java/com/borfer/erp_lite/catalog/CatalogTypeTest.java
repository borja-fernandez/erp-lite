package com.borfer.erp_lite.catalog;

import com.borfer.erp_lite.domain.catalog.CatalogType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CatalogType Enum Test")
public class CatalogTypeTest {

    @Test
    @DisplayName("Should throw IllegalArgumentException when code is null")
    void should_throw_IllegalArgumentException_when_code_is_null() {
        final String message = "Catalog type code cannot be null or empty";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            CatalogType.fromCode(null);
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when code is blank")
    void should_throw_IllegalArgumentException_when_code_is_blank() {
        final String message = "Catalog type code cannot be null or empty";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            CatalogType.fromCode("  ");
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when code is invalid")
    void should_throw_IllegalArgumentException_when_code_is_invalid() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            CatalogType.fromCode("INVALID_CODE");
        });

        assertTrue(exception.getMessage().contains("INVALID_CODE"));
    }

    @Test
    @DisplayName("Should return correct enum for each valid code")
    void should_return_correct_enum_for_each_valid_code() {
        assertEquals(CatalogType.fromCode("PRODUCT_CATEGORIES"), CatalogType.PRODUCT_CATEGORIES);
        assertEquals(CatalogType.fromCode("ORDER_STATUSES"), CatalogType.ORDER_STATUSES);
        assertEquals(CatalogType.fromCode("PAYMENT_METHODS"), CatalogType.PAYMENT_METHODS);
        assertEquals(CatalogType.fromCode("SHIPPING_METHODS"), CatalogType.SHIPPING_METHODS);
        assertEquals(CatalogType.fromCode("COUNTRIES"), CatalogType.COUNTRIES);
        assertEquals(CatalogType.fromCode("CURRENCIES"), CatalogType.CURRENCIES);
    }

    @Test
    @DisplayName("Should return true for isValid when code is valid")
    void should_return_true_for_isValid_when_code_is_valid() {
        assertTrue(CatalogType.isValid("PRODUCT_CATEGORIES"));
    }

    @Test
    @DisplayName("Should return false for isValid when code is invalid")
    void should_return_false_for_isValid_when_code_is_invalid() {
        assertFalse(CatalogType.isValid("NOT_A_TYPE"));
        assertFalse(CatalogType.isValid(null));
    }

    @Test
    @DisplayName("Should return correct code and displayName for each enum value")
    void should_return_correct_code_and_displayName_for_each_enum_value() {
        assertEquals(CatalogType.PRODUCT_CATEGORIES.getCode(), "PRODUCT_CATEGORIES");
        assertEquals(CatalogType.PRODUCT_CATEGORIES.getDisplayName(), "Product Categories");
        assertEquals(CatalogType.ORDER_STATUSES.getCode(), "ORDER_STATUSES");
        assertEquals(CatalogType.CURRENCIES.getDisplayName(), "Currencies");
    }
}
