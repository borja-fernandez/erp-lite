package com.borfer.erp_lite.persistence.mongo.document;

public record CatalogItem(
        String id,
        String code,
        String value,
        String description,
        Integer displayOrder,
        CatalogItemMetadata metadata
) {}