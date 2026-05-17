package com.borfer.erp_lite.product;

import com.borfer.erp_lite.domain.product.*;
import com.borfer.erp_lite.domain.product.events.ProductCreated;
import com.borfer.erp_lite.domain.product.events.ProductDeactivated;
import com.borfer.erp_lite.domain.product.events.ProductUpdated;
import com.borfer.erp_lite.domain.product.events.StockChanged;
import com.borfer.erp_lite.domain.shared.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Product Aggregate Test")
public class ProductTest {

    private static final Currency USD = Currency.getInstance("USD");

    private Product buildProduct() {
        return Product.create(
                SKU.of("PROD-001"),
                ProductName.of("Test Product"),
                "A test product description",
                Money.of(10.00, USD),
                Stock.of(100),
                CategoryReference.of("electronics"),
                ProductImage.of("https://example.com/image.jpg"),
                "system"
        );
    }

    @Test
    @DisplayName("Should throw NullPointerException when SKU is null")
    void should_throw_NullPointerException_when_SKU_is_null() {
        final String message = "SKU cannot be null";

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            Product.create(null, ProductName.of("Name"), "desc",
                    Money.of(10.00, USD), Stock.of(10),
                    CategoryReference.of("cat"),
                    ProductImage.of("https://example.com/img.jpg"), "system");
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should throw NullPointerException when ProductName is null")
    void should_throw_NullPointerException_when_ProductName_is_null() {
        final String message = "ProductName cannot be null";

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            Product.create(SKU.of("PROD-001"), null, "desc",
                    Money.of(10.00, USD), Stock.of(10),
                    CategoryReference.of("cat"),
                    ProductImage.of("https://example.com/img.jpg"), "system");
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should throw NullPointerException when price is null")
    void should_throw_NullPointerException_when_price_is_null() {
        final String message = "Price cannot be null";

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            Product.create(SKU.of("PROD-001"), ProductName.of("Name"), "desc",
                    null, Stock.of(10), CategoryReference.of("cat"),
                    ProductImage.of("https://example.com/img.jpg"), "system");
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should throw NullPointerException when stock is null")
    void should_throw_NullPointerException_when_stock_is_null() {
        final String message = "Stock cannot be null";

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            Product.create(SKU.of("PROD-001"), ProductName.of("Name"), "desc",
                    Money.of(10.00, USD), null, CategoryReference.of("cat"),
                    ProductImage.of("https://example.com/img.jpg"), "system");
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should throw NullPointerException when category is null")
    void should_throw_NullPointerException_when_category_is_null() {
        final String message = "Category cannot be null";

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            Product.create(SKU.of("PROD-001"), ProductName.of("Name"), "desc",
                    Money.of(10.00, USD), Stock.of(10), null,
                    ProductImage.of("https://example.com/img.jpg"), "system");
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should throw NullPointerException when createdBy is null")
    void should_throw_NullPointerException_when_createdBy_is_null() {
        final String message = "createdBy cannot be null";

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            Product.create(SKU.of("PROD-001"), ProductName.of("Name"), "desc",
                    Money.of(10.00, USD), Stock.of(10),
                    CategoryReference.of("cat"),
                    ProductImage.of("https://example.com/img.jpg"), null);
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should create Product successfully with valid parameters")
    void should_create_Product_successfully_with_valid_parameters() {
        Product product = buildProduct();

        assertNotNull(product.getId());
        assertEquals(product.getSku(), SKU.of("PROD-001"));
        assertEquals(product.getName(), ProductName.of("Test Product"));
        assertEquals(product.getStock(), Stock.of(100));
        assertTrue(product.isActive());
        assertNotNull(product.getAuditInfo());
    }

    @Test
    @DisplayName("Should register ProductCreated event on create")
    void should_register_ProductCreated_event_on_create() {
        Product product = buildProduct();

        assertEquals(product.getDomainEvents().size(), 1);
        assertInstanceOf(ProductCreated.class, product.getDomainEvents().get(0));
    }

    @Test
    @DisplayName("Should clear domain events after clearDomainEvents")
    void should_clear_domain_events_after_clearDomainEvents() {
        Product product = buildProduct();

        product.clearDomainEvents();

        assertTrue(product.getDomainEvents().isEmpty());
    }

    @Test
    @DisplayName("Should throw NullPointerException when updating with null name")
    void should_throw_NullPointerException_when_updating_with_null_name() {
        Product product = buildProduct();

        assertThrows(NullPointerException.class, () -> {
            product.update(null, "desc", Money.of(10.00, USD),
                    CategoryReference.of("cat"), ProductImage.of("https://example.com/img.jpg"));
        });
    }

    @Test
    @DisplayName("Should throw NullPointerException when updating with null price")
    void should_throw_NullPointerException_when_updating_with_null_price() {
        Product product = buildProduct();

        assertThrows(NullPointerException.class, () -> {
            product.update(ProductName.of("New Name"), "desc", null,
                    CategoryReference.of("cat"), ProductImage.of("https://example.com/img.jpg"));
        });
    }

    @Test
    @DisplayName("Should throw NullPointerException when updating with null category")
    void should_throw_NullPointerException_when_updating_with_null_category() {
        Product product = buildProduct();

        assertThrows(NullPointerException.class, () -> {
            product.update(ProductName.of("New Name"), "desc",
                    Money.of(10.00, USD), null,
                    ProductImage.of("https://example.com/img.jpg"));
        });
    }

    @Test
    @DisplayName("Should update Product fields and register ProductUpdated event")
    void should_update_Product_fields_and_register_ProductUpdated_event() {
        Product product = buildProduct();
        product.clearDomainEvents();

        product.update(ProductName.of("Updated Name"), "New desc",
                Money.of(20.00, USD), CategoryReference.of("clothing"), null);

        assertEquals(product.getName(), ProductName.of("Updated Name"));
        assertEquals(product.getPrice(), Money.of(20.00, USD));
        assertEquals(product.getDomainEvents().size(), 1);
        assertInstanceOf(ProductUpdated.class, product.getDomainEvents().get(0));
    }

    @Test
    @DisplayName("Should increment stock and register StockChanged event")
    void should_increment_stock_and_register_StockChanged_event() {
        Product product = buildProduct();
        product.clearDomainEvents();

        product.incrementStock(10, "restock");

        assertEquals(product.getStock().value(), 110);
        assertEquals(product.getDomainEvents().size(), 1);
        assertInstanceOf(StockChanged.class, product.getDomainEvents().get(0));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when incrementing stock by zero or negative")
    void should_throw_IllegalArgumentException_when_incrementing_stock_by_zero_or_negative() {
        Product product = buildProduct();

        assertThrows(IllegalArgumentException.class, () -> product.incrementStock(0, "reason"));
        assertThrows(IllegalArgumentException.class, () -> product.incrementStock(-1, "reason"));
    }

    @Test
    @DisplayName("Should decrement stock and register StockChanged event")
    void should_decrement_stock_and_register_StockChanged_event() {
        Product product = buildProduct();
        product.clearDomainEvents();

        product.decrementStock(10, "sale");

        assertEquals(product.getStock().value(), 90);
        assertEquals(product.getDomainEvents().size(), 1);
        assertInstanceOf(StockChanged.class, product.getDomainEvents().get(0));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when decrementing stock by zero or negative")
    void should_throw_IllegalArgumentException_when_decrementing_stock_by_zero_or_negative() {
        Product product = buildProduct();

        assertThrows(IllegalArgumentException.class, () -> product.decrementStock(0, "reason"));
        assertThrows(IllegalArgumentException.class, () -> product.decrementStock(-1, "reason"));
    }

    @Test
    @DisplayName("Should change price and register ProductUpdated event")
    void should_change_price_and_register_ProductUpdated_event() {
        Product product = buildProduct();
        final Money newPrice = Money.of(25.00, USD);
        product.clearDomainEvents();

        product.changePrice(newPrice);

        assertEquals(product.getPrice(), newPrice);
        assertEquals(product.getDomainEvents().size(), 1);
        assertInstanceOf(ProductUpdated.class, product.getDomainEvents().get(0));
    }

    @Test
    @DisplayName("Should throw NullPointerException when changing price to null")
    void should_throw_NullPointerException_when_changing_price_to_null() {
        Product product = buildProduct();

        assertThrows(NullPointerException.class, () -> product.changePrice(null));
    }

    @Test
    @DisplayName("Should deactivate Product and register ProductDeactivated event")
    void should_deactivate_Product_and_register_ProductDeactivated_event() {
        Product product = buildProduct();
        product.clearDomainEvents();

        product.deactivate();

        assertFalse(product.isActive());
        assertEquals(product.getDomainEvents().size(), 1);
        assertInstanceOf(ProductDeactivated.class, product.getDomainEvents().get(0));
    }

    @Test
    @DisplayName("Should throw IllegalStateException when deactivating an already deactivated product")
    void should_throw_IllegalStateException_when_deactivating_already_deactivated_product() {
        final String message = "Product is already deactivated";
        Product product = buildProduct();
        product.deactivate();

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            product.deactivate();
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should activate a deactivated Product")
    void should_activate_a_deactivated_Product() {
        Product product = buildProduct();
        product.deactivate();

        product.activate();

        assertTrue(product.isActive());
    }

    @Test
    @DisplayName("Should return true when stock is sufficient")
    void should_return_true_when_stock_is_sufficient() {
        Product product = buildProduct();

        assertTrue(product.hasAvailableStock(100));
    }

    @Test
    @DisplayName("Should return false when required stock exceeds available")
    void should_return_false_when_required_stock_exceeds_available() {
        Product product = buildProduct();

        assertFalse(product.hasAvailableStock(101));
    }
}
