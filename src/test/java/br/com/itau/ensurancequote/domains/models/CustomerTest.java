package br.com.itau.ensurancequote.domains.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class CustomerTest {

    @Test
    void testGetters() {
        final Customer customer = new Customer(
                "123456789",
                "John Doe",
                "NATURAL",
                "MALE",
                LocalDate.of(1980, 1, 1),
                "john@example.com",
                "1234567890"
        );
        assertEquals("123456789", customer.getDocumentNumber());
        assertEquals("John Doe", customer.getName());
        assertEquals("NATURAL", customer.getType());
        assertEquals("MALE", customer.getGender());
        assertEquals(LocalDate.of(1980, 1, 1), customer.getDateOfBirth());
        assertEquals("john@example.com", customer.getEmail());
        assertEquals("1234567890", customer.getPhoneNumber());
    }

    @Test
    void testEqualsAndHashCode() {
        final Customer customer1 = new Customer(
                "123456789",
                "John Doe",
                "NATURAL",
                "MALE",
                LocalDate.of(1980, 1, 1),
                "john@example.com",
                "1234567890"
        );
        final Customer customer2 = new Customer(
                "123456789",
                "Jane Doe",
                "NATURAL",
                "FEMALE",
                LocalDate.of(1990, 1, 1),
                "jane@example.com",
                "0987654321"
        );
        final Customer customer3 = new Customer(
                "987654321",
                "Alice",
                "NATURAL",
                "FEMALE",
                LocalDate.of(1985, 1, 1),
                "alice@example.com",
                "1112223333"
        );

        assertEquals(customer1, customer2);
        assertEquals(customer1.hashCode(), customer2.hashCode());
        assertNotEquals(customer1, customer3);
    }
}
