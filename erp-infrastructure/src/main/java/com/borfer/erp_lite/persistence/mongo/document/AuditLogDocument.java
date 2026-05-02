package com.borfer.erp_lite.persistence.mongo.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "audit_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLogDocument {

    @Id
    private String id;

    private String userId;
    private String className;
    private String methodName;
    private String endpoint;
    private String ipAddress;
    private Boolean success;
    private String errorMessage;
    private Long executionTimeMs;
    private Instant timestamp;
}