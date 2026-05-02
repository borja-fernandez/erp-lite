package com.borfer.erp_lite.persistence.mongo.repository;

import com.borfer.erp_lite.persistence.mongo.document.AuditLogDocument;
import com.borfer.erp_lite.persistence.mongo.document.CatalogDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuditLogRepository extends MongoRepository<AuditLogDocument, ObjectId> {
}
