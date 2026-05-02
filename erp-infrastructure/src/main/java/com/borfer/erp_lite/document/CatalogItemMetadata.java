package com.borfer.erp_lite.document;

import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;
import java.util.List;

public record CatalogItemMetadata(
        String icon,
        String color,
        List<String> nextStatuses,
        @Field(targetType = FieldType.DECIMAL128) BigDecimal fee
) {}