package com.borfer.erp_lite.shared;

import com.borfer.erp_lite.domain.shared.AuditInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AuditInfo Value Object Test")
public class AuditInfoTest {

    @Test
    @DisplayName("Should throw NullPointerException when createdBy is null")
    void should_throw_NullPointerException_when_createdBy_is_null() {
        final String message = "createdBy cannot be null";
        final Instant now = Instant.now();

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            new AuditInfo(null, now, now);
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when createdBy is blank")
    void should_throw_IllegalArgumentException_when_createdBy_is_blank() {
        final String message = "createdBy cannot be blank";
        final Instant now = Instant.now();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new AuditInfo("  ", now, now);
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should throw NullPointerException when createdAt is null")
    void should_throw_NullPointerException_when_createdAt_is_null() {
        final String message = "createdAt cannot be null";
        final Instant now = Instant.now();

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            new AuditInfo("system", null, now);
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should throw NullPointerException when updatedAt is null")
    void should_throw_NullPointerException_when_updatedAt_is_null() {
        final String message = "updatedAt cannot be null";
        final Instant now = Instant.now();

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            new AuditInfo("system", now, null);
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should create AuditInfo successfully with valid parameters")
    void should_create_AuditInfo_successfully_with_valid_parameters() {
        final Instant now = Instant.now();

        AuditInfo auditInfo = new AuditInfo("system", now, now);

        assertEquals(auditInfo.createdBy(), "system");
        assertEquals(auditInfo.createdAt(), now);
        assertEquals(auditInfo.updatedAt(), now);
    }

    @Test
    @DisplayName("Should create AuditInfo with same createdAt and updatedAt using factory create")
    void should_create_AuditInfo_with_same_timestamps_using_factory_create() {
        final Instant timestamp = Instant.now();

        AuditInfo auditInfo = AuditInfo.create("system", timestamp);

        assertEquals(auditInfo.createdAt(), timestamp);
        assertEquals(auditInfo.updatedAt(), timestamp);
    }

    @Test
    @DisplayName("Should return new AuditInfo with updated timestamp preserving createdAt")
    void should_return_new_AuditInfo_with_updated_timestamp_preserving_createdAt() {
        final Instant timestamp = Instant.now();
        AuditInfo original = AuditInfo.create("system", timestamp);

        AuditInfo updated = original.updateTimestamp();

        assertEquals(updated.createdBy(), original.createdBy());
        assertEquals(updated.createdAt(), original.createdAt());
        assertNotNull(updated.updatedAt());
    }
}
