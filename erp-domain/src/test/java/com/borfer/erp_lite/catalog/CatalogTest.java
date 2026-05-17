package com.borfer.erp_lite.catalog;

import com.borfer.erp_lite.domain.catalog.Catalog;
import com.borfer.erp_lite.domain.catalog.CatalogItem;
import com.borfer.erp_lite.domain.catalog.CatalogType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Catalog Aggregate Test")
public class CatalogTest {

    private CatalogItem buildActiveItem(String id) {
        return new CatalogItem(id, "CODE_" + id, "Value", "Description", 1, Map.of());
    }

    private CatalogItem buildInactiveItem(String id) {
        CatalogItem item = new CatalogItem(id, "CODE_" + id, "Value", "Description", 2, Map.of());
        item.turnOffStatus();
        return item;
    }

    private Catalog buildCatalog(List<CatalogItem> items) {
        return new Catalog(
                UUID.randomUUID().toString(),
                CatalogType.PRODUCT_CATEGORIES,
                "Product Categories",
                "All product categories",
                items,
                true
        );
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when id is null")
    void should_throw_IllegalArgumentException_when_id_is_null() {
        final String message = "Entity ID cannot be null";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Catalog(null, CatalogType.PRODUCT_CATEGORIES, "Name", "Desc", List.of(), true);
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when catalogType is null")
    void should_throw_IllegalArgumentException_when_catalogType_is_null() {
        final String message = "Type code cannot be null or empty";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Catalog(UUID.randomUUID().toString(), null, "Name", "Desc", List.of(), true);
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when name is null")
    void should_throw_IllegalArgumentException_when_name_is_null() {
        final String message = "Name cannot be null or empty";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Catalog(UUID.randomUUID().toString(), CatalogType.PRODUCT_CATEGORIES, null, "Desc", List.of(), true);
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when name is empty")
    void should_throw_IllegalArgumentException_when_name_is_empty() {
        final String message = "Name cannot be null or empty";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Catalog(UUID.randomUUID().toString(), CatalogType.PRODUCT_CATEGORIES, "", "Desc", List.of(), true);
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should create Catalog successfully with valid parameters")
    void should_create_Catalog_successfully_with_valid_parameters() {
        final String name = "Product Categories";
        final CatalogType type = CatalogType.PRODUCT_CATEGORIES;

        Catalog catalog = buildCatalog(List.of());

        assertEquals(catalog.getName(), name);
        assertEquals(catalog.getCatalogType(), type);
        assertTrue(catalog.isActive());
    }

    @Test
    @DisplayName("Should find item by id when item exists")
    void should_find_item_by_id_when_item_exists() {
        final String itemId = UUID.randomUUID().toString();
        CatalogItem item = buildActiveItem(itemId);
        Catalog catalog = buildCatalog(List.of(item));

        assertTrue(catalog.findItemByCode(itemId).isPresent());
        assertEquals(catalog.findItemByCode(itemId).get(), item);
    }

    @Test
    @DisplayName("Should return empty Optional when item id does not exist")
    void should_return_empty_Optional_when_item_id_does_not_exist() {
        Catalog catalog = buildCatalog(List.of(buildActiveItem(UUID.randomUUID().toString())));

        assertTrue(catalog.findItemByCode("non-existing-id").isEmpty());
    }

    @Test
    @DisplayName("Should return true when catalog contains item with given id")
    void should_return_true_when_catalog_contains_item_with_given_id() {
        final String itemId = UUID.randomUUID().toString();
        Catalog catalog = buildCatalog(List.of(buildActiveItem(itemId)));

        assertTrue(catalog.containsItem(itemId));
    }

    @Test
    @DisplayName("Should return false when catalog does not contain item with given id")
    void should_return_false_when_catalog_does_not_contain_item_with_given_id() {
        Catalog catalog = buildCatalog(List.of(buildActiveItem(UUID.randomUUID().toString())));

        assertFalse(catalog.containsItem("non-existing-id"));
    }

    @Test
    @DisplayName("Should return only active items from findActiveItems")
    void should_return_only_active_items_from_findActiveItems() {
        CatalogItem activeItem = buildActiveItem(UUID.randomUUID().toString());
        CatalogItem inactiveItem = buildInactiveItem(UUID.randomUUID().toString());
        Catalog catalog = buildCatalog(List.of(activeItem, inactiveItem));

        List<CatalogItem> activeItems = catalog.findActiveItems();

        assertEquals(activeItems.size(), 1);
        assertEquals(activeItems.get(0), activeItem);
    }

    @Test
    @DisplayName("Should return all items from findAllItems")
    void should_return_all_items_from_findAllItems() {
        CatalogItem active = buildActiveItem(UUID.randomUUID().toString());
        CatalogItem inactive = buildInactiveItem(UUID.randomUUID().toString());
        Catalog catalog = buildCatalog(List.of(active, inactive));

        List<CatalogItem> all = catalog.findAllItems();

        assertEquals(all.size(), 2);
    }

    @Test
    @DisplayName("Should return unmodifiable list from findAllItems")
    void should_return_unmodifiable_list_from_findAllItems() {
        Catalog catalog = buildCatalog(List.of(buildActiveItem(UUID.randomUUID().toString())));

        assertThrows(UnsupportedOperationException.class, () -> {
            catalog.findAllItems().add(buildActiveItem(UUID.randomUUID().toString()));
        });
    }
}
