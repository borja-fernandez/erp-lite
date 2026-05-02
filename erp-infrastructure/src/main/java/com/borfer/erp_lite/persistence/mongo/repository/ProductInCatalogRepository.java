package com.borfer.erp_lite.persistence.mongo.repository;

import com.borfer.erp_lite.persistence.mongo.document.ProductInCatalogDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductInCatalogRepository extends MongoRepository<ProductInCatalogDocument,String > {
}
