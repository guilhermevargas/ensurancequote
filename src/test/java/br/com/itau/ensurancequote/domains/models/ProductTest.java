package br.com.itau.ensurancequote.domains.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @Test
    void testBuilderAndGetters() {
        LocalDateTime now = LocalDateTime.now();
        Product product = new Product.Builder()
                .id("prod1")
                .name("Insurance Product")
                .createdAt(now)
                .active(true)
                .offers(List.of("offer1", "offer2"))
                .build();

        assertEquals("prod1", product.getId());
        assertEquals("Insurance Product", product.getName());
        assertEquals(now, product.getCreatedAt());
        assertTrue(product.isActive());
        assertEquals(List.of("offer1", "offer2"), product.getOffers());
    }


    @Test
    void testEqualsAndHashCode() {
        LocalDateTime now = LocalDateTime.now();
        Product product1 = new Product("prod1", "Insurance Product", now, true, List.of("offer1"));
        Product product2 = new Product("prod1", "Different Name", now.plusMinutes(5), false, List.of("offer2"));
        Product product3 = new Product("prod3", "Insurance Product", now, true, List.of("offer1"));

        assertEquals(product1, product2);
        assertEquals(product1.hashCode(), product2.hashCode());
        assertNotEquals(product1, product3);
    }

    @Test
    void testToString() {
        LocalDateTime now = LocalDateTime.now();
        Product product = new Product("prod1", "Insurance Product", now, true, List.of("offer1"));
        String str = product.toString();
        assertTrue(str.contains("prod1"));
        assertTrue(str.contains("Insurance Product"));
        assertTrue(str.contains(now.toString()));
        assertTrue(str.contains("offer1"));
    }
}
