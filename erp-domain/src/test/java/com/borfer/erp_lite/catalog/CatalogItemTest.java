package com.borfer.erp_lite.catalog;

import com.borfer.erp_lite.domain.catalog.CatalogItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CatalogItem Domain Test")
public class CatalogItemTest {

    @Test
    @DisplayName("Should throw IllegalArgumentException when code is null or empty")
    void should_throw_IllegalArgumentException_when_code_is_null_or_empty() {
        final String message = "Code cannot be null or empty";

        IllegalArgumentException exception_for_null = assertThrows(IllegalArgumentException.class, () -> {
            new CatalogItem(
                    UUID.randomUUID().toString(),
                    null,
                    "Some value",
                    "This product is a test",
                    1,
                    Map.of("Value Display", "Test")
            );
        });

        assertEquals(exception_for_null.getMessage(), message);

        IllegalArgumentException exception_for_empty = assertThrows(IllegalArgumentException.class, () -> {
            new CatalogItem(
                    UUID.randomUUID().toString(),
                    "",
                    "Some value",
                    "This product is a test",
                    1,
                    Map.of("Value Display", "Test")
            );
        });

        assertEquals(exception_for_empty.getMessage(), message);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when id is null")
    void should_throw_IllegalArgumentException_when_id_is_null() {
        final String message = "Entity ID cannot be null";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new CatalogItem(
                    null,
                    "ITEM_001",
                    "Some value",
                    "This product is a test",
                    1,
                    Map.of("Value Display", "Test")
            );
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should create CatalogItem successfully when all parameters are valid")
    void should_create_CatalogItem_successfully_when_all_params_are_valid() {
        final String id = UUID.randomUUID().toString();
        final String code = "ITEM_001";
        final String value = "Some value";
        final String description = "This product is a test";
        final int displayOrder = 1;

        CatalogItem item = new CatalogItem(id, code, value, description, displayOrder, Map.of("Value Display", "Test"));

        assertEquals(item.getId(), id);
        assertEquals(item.getCode(), code);
        assertEquals(item.getValue(), value);
        assertEquals(item.getDescription(), description);
        assertEquals(item.getDisplayOrder(), displayOrder);
    }

    @Test
    @DisplayName("Should be active after creation")
    void should_be_active_after_creation() {
        CatalogItem item = new CatalogItem(
                UUID.randomUUID().toString(),
                "ITEM_001",
                "Some value",
                "This product is a test",
                1,
                Map.of("Value Display", "Test")
        );

        assertTrue(item.isActive());
    }

    @Test
    @DisplayName("Should use empty map when metadata is null")
    void should_use_empty_map_when_metadata_is_null() {
        CatalogItem item = new CatalogItem(
                UUID.randomUUID().toString(),
                "ITEM_001",
                "Some value",
                "This product is a test",
                1,
                null
        );

        assertFalse(item.hasMetadata("anyKey"));
    }

    @Test
    @DisplayName("Should return metadata value when key exists")
    void should_return_metadata_value_when_key_exists() {
        final String key = "Value Display";
        final String value = "Test";

        CatalogItem item = new CatalogItem(
                UUID.randomUUID().toString(),
                "ITEM_001",
                "Some value",
                "This product is a test",
                1,
                Map.of(key, value)
        );

        assertEquals(item.getMetadata(key), value);
    }

    @Test
    @DisplayName("Should return null when metadata key does not exist")
    void should_return_null_when_metadata_key_does_not_exist() {
        CatalogItem item = new CatalogItem(
                UUID.randomUUID().toString(),
                "ITEM_001",
                "Some value",
                "This product is a test",
                1,
                Map.of("Value Display", "Test")
        );

        assertNull(item.getMetadata("nonExistentKey"));
    }

    @Test
    @DisplayName("Should return true when metadata has key")
    void should_return_true_when_metadata_has_key() {
        final String key = "Value Display";

        CatalogItem item = new CatalogItem(
                UUID.randomUUID().toString(),
                "ITEM_001",
                "Some value",
                "This product is a test",
                1,
                Map.of(key, "Test")
        );

        assertTrue(item.hasMetadata(key));
    }

    @Test
    @DisplayName("Should return false when metadata does not have key")
    void should_return_false_when_metadata_does_not_have_key() {
        CatalogItem item = new CatalogItem(
                UUID.randomUUID().toString(),
                "ITEM_001",
                "Some value",
                "This product is a test",
                1,
                Map.of("Value Display", "Test")
        );

        assertFalse(item.hasMetadata("nonExistentKey"));
    }

    @Test
    @DisplayName("Should set isActive to false when turnOffStatus is called")
    void should_set_isActive_to_false_when_turnOffStatus_is_called() {
        CatalogItem item = new CatalogItem(
                UUID.randomUUID().toString(),
                "ITEM_001",
                "Some value",
                "This product is a test",
                1,
                Map.of("Value Display", "Test")
        );

        item.turnOffStatus();

        assertFalse(item.isActive());
    }

    @Test
    @DisplayName("Should set isActive to true when turnOnStatus is called")
    void should_set_isActive_to_true_when_turnOnStatus_is_called() {
        CatalogItem item = new CatalogItem(
                UUID.randomUUID().toString(),
                "ITEM_001",
                "Some value",
                "This product is a test",
                1,
                Map.of("Value Display", "Test")
        );

        item.turnOffStatus();
        item.turnOnStatus();

        assertTrue(item.isActive());
    }

}