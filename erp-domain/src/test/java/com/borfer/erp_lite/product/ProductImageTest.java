package com.borfer.erp_lite.product;

import com.borfer.erp_lite.domain.product.ProductImage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ProductImage Value Object Test")
public class ProductImageTest {

    @Test
    @DisplayName("Should throw NullPointerException when imageUrl is null")
    void should_throw_NullPointerException_when_imageUrl_is_null() {
        final String message = "ImageUrl cannot be null";

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            new ProductImage(null);
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when URL has no scheme")
    void should_throw_IllegalArgumentException_when_URL_has_no_scheme() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new ProductImage("example.com/image.jpg");
        });

        assertTrue(exception.getMessage().startsWith("Invalid URL:"));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when URL is malformed")
    void should_throw_IllegalArgumentException_when_URL_is_malformed() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProductImage("https://invalid domain.com/image");
        });
    }

    @Test
    @DisplayName("Should create ProductImage successfully with a valid URL")
    void should_create_ProductImage_successfully_with_a_valid_URL() {
        final String url = "https://example.com/images/product.jpg";

        ProductImage image = new ProductImage(url);

        assertEquals(image.imageUrl(), url);
    }

    @Test
    @DisplayName("Should create ProductImage using factory method of")
    void should_create_ProductImage_using_factory_method_of() {
        final String url = "https://cdn.example.com/img.png";

        ProductImage image = ProductImage.of(url);

        assertEquals(image.imageUrl(), url);
    }

    @Test
    @DisplayName("Should return full URL from getFullUrl")
    void should_return_full_URL_from_getFullUrl() {
        final String url = "https://example.com/images/product.jpg";
        ProductImage image = ProductImage.of(url);

        assertEquals(image.getFullUrl(), url);
    }

    @Test
    @DisplayName("Should return filename from URL with path")
    void should_return_filename_from_URL_with_path() {
        ProductImage image = ProductImage.of("https://example.com/images/product.jpg");

        assertEquals(image.getFileName(), "product.jpg");
    }

    @Test
    @DisplayName("Should return full URL as filename when URL has no path slash")
    void should_return_full_URL_as_filename_when_URL_has_no_path_slash() {
        final String url = "file:imagename";
        ProductImage image = ProductImage.of(url);

        assertEquals(image.getFileName(), url);
    }
}
