package com.borfer.erp_lite.domain.shared;

import java.time.Instant;
import java.util.Objects;

public record AuditInfo(String createdBy, Instant createdAt, Instant updatedAt) {

    public AuditInfo {
        Objects.requireNonNull(createdBy, "createdBy cannot be null");
        if (createdBy.isBlank()) throw new IllegalArgumentException("createdBy cannot be blank");
        Objects.requireNonNull(createdAt, "createdAt cannot be null");
        Objects.requireNonNull(updatedAt, "updatedAt cannot be null");
    }

    public static AuditInfo create(String createdBy, Instant timestamp) {
        return new AuditInfo(createdBy, timestamp, timestamp);
    }

    public AuditInfo updateTimestamp() {
        return new AuditInfo(this.createdBy, this.createdAt, Instant.now());
    }
}