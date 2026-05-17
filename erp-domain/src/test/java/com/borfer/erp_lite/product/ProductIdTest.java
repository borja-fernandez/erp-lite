package com.borfer.erp_lite.product;

import com.borfer.erp_lite.domain.product.ProductId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ProductId Value Object Test")
public class ProductIdTest {

    @Test
    @DisplayName("Should throw NullPointerException when value is null")
    void should_throw_NullPointerException_when_value_is_null() {
        final String message = "ProductId cannot be null";

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            new ProductId(null);
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should create ProductId successfully when value is valid")
    void should_create_ProductId_successfully_when_value_is_valid() {
        final UUID uuid = UUID.randomUUID();

        ProductId productId = new ProductId(uuid);

        assertEquals(productId.value(), uuid);
    }

    @Test
    @DisplayName("Should create ProductId using factory method of")
    void should_create_ProductId_using_factory_method_of() {
        final UUID uuid = UUID.randomUUID();

        ProductId productId = ProductId.of(uuid);

        assertEquals(productId.value(), uuid);
    }

    @Test
    @DisplayName("Should generate a non-null ProductId using generate")
    void should_generate_a_non_null_ProductId_using_generate() {
        ProductId productId = ProductId.generate();

        assertNotNull(productId.value());
    }

    @Test
    @DisplayName("Should generate unique ProductIds on each call")
    void should_generate_unique_ProductIds_on_each_call() {
        ProductId first = ProductId.generate();
        ProductId second = ProductId.generate();

        assertNotEquals(first, second);
    }
}
