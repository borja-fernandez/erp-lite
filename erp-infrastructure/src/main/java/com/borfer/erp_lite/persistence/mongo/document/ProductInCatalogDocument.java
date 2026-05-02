package com.borfer.erp_lite.persistence.mongo.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Document(collection = "product_documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductInCatalogDocument {

    @Id
    private String id;

    private String sku;
    private String name;
    private String description;

    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal price;

    private String currency;
    private Integer stock;
    private Boolean active;
    private String categoryId;
    private String categoryName;
    private String imageUrl;
    private ProductSpecifications specifications;
    private List<String> tags;
    private Instant createdAt;
    private Instant updatedAt;
}