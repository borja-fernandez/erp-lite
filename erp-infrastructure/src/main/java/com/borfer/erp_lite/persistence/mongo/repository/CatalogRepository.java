package com.borfer.erp_lite.persistence.mongo.repository;

import com.borfer.erp_lite.persistence.mongo.document.CatalogDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CatalogRepository extends MongoRepository<CatalogDocument,String > {
}
