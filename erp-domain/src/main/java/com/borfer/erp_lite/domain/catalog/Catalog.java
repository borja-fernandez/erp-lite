package com.borfer.erp_lite.domain.catalog;

import com.borfer.erp_lite.domain.common.AggregateRoot;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Catalog extends AggregateRoot<String> {

    private final CatalogType catalogType;
    private final String name;
    private final String description;
    private final List<CatalogItem> items;
    private final boolean isActive;

    public Catalog(String id,
                   CatalogType catalogType,
                   String name,
                   String description,
                   List<CatalogItem> items,
                   boolean isActive) {
        super(id);

        if (catalogType == null) {
            throw new IllegalArgumentException("Type code cannot be null or empty");
        }

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }

        this.catalogType = catalogType;
        this.name = name;
        this.description = description;
        this.items = items;
        this.isActive = isActive;
    }

    public Optional<CatalogItem> findItemByCode(String itemId){
        return this.items
                .stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst();
    }

    public boolean containsItem(String itemId){
        return this.findItemByCode(itemId).isPresent();
    }

    public List<CatalogItem> findActiveItems() {
        return this.items
                .stream()
                .filter(CatalogItem::isActive)
                .toList();
    }

    public List<CatalogItem> findAllItems(){
       return Collections.unmodifiableList(this.items);
    }
}
