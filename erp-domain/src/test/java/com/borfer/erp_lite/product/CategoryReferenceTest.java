package com.borfer.erp_lite.product;

import com.borfer.erp_lite.domain.product.CategoryReference;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CategoryReference Value Object Test")
public class CategoryReferenceTest {

    @Test
    @DisplayName("Should throw NullPointerException when categoryId is null")
    void should_throw_NullPointerException_when_categoryId_is_null() {
        final String message = "CategoryId cannot be null";

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            new CategoryReference(null);
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when categoryId is blank")
    void should_throw_IllegalArgumentException_when_categoryId_is_blank() {
        final String message = "CategoryId cannot be blank";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new CategoryReference("  ");
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should create CategoryReference successfully when categoryId is valid")
    void should_create_CategoryReference_successfully_when_categoryId_is_valid() {
        final String categoryId = "electronics";

        CategoryReference reference = new CategoryReference(categoryId);

        assertEquals(reference.categoryId(), categoryId);
    }

    @Test
    @DisplayName("Should create CategoryReference using factory method of")
    void should_create_CategoryReference_using_factory_method_of() {
        CategoryReference reference = CategoryReference.of("clothing");

        assertEquals(reference.categoryId(), "clothing");
    }
}
