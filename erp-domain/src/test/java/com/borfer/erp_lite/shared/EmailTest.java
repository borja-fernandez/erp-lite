package com.borfer.erp_lite.shared;

import com.borfer.erp_lite.domain.shared.Email;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Email Value Object Test")
public class EmailTest {

    @Test
    @DisplayName("Should throw NullPointerException when value is null")
    void should_throw_NullPointerException_when_value_is_null() {
        final String message = "Email cannot be null";

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            new Email(null);
        });

        assertEquals(exception.getMessage(), message);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when email format is invalid")
    void should_throw_IllegalArgumentException_when_email_format_is_invalid() {
        final String invalidEmail = "not-an-email";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Email(invalidEmail);
        });

        assertTrue(exception.getMessage().contains(invalidEmail));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when email has no domain")
    void should_throw_IllegalArgumentException_when_email_has_no_domain() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Email("user@");
        });
    }

    @Test
    @DisplayName("Should create Email successfully when format is valid")
    void should_create_Email_successfully_when_format_is_valid() {
        final String validEmail = "user@example.com";

        Email email = new Email(validEmail);

        assertEquals(email.value(), validEmail);
    }

    @Test
    @DisplayName("Should create Email using factory method of")
    void should_create_Email_using_factory_method_of() {
        Email email = Email.of("user@domain.org");

        assertNotNull(email.value());
    }
}
