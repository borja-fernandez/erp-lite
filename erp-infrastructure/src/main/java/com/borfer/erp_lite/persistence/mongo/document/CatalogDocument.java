package com.borfer.erp_lite.persistence.mongo.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document(collection = "catalogs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CatalogDocument {

    @Id
    private String id;

    private String name;
    private String description;
    private CatalogType catalogType;
    private Boolean active;
    private List<CatalogItem> items;
    private Instant createdAt;
    private Instant updatedAt;
}