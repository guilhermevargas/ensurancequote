package br.com.itau.ensurancequote.gateways;

import br.com.itau.ensurancequote.domains.models.Customer;
import br.com.itau.ensurancequote.domains.models.Quote;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class QuoteGatewayTest {

    @Autowired
    private QuoteGateway quoteGateway; // the impl

    @Test
    void shouldPersistAndFindQuote() {
        final Quote quote = new Quote(
                null,
                "prod-123",
                "offer-abc",
                "HOME",
                BigDecimal.valueOf(75.25),
                BigDecimal.valueOf(825000),
                Map.of("Incendio", BigDecimal.valueOf(250000), "Desastres naturais", BigDecimal.valueOf(500000)),
                List.of("Encanador", "Eletricista"),
                new Customer("36205578900", "John Wick", "NATURAL", "MALE", LocalDate.of(1973, 5, 2), "john@test.com", "11950503030"),
                null,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        final Quote saved = quoteGateway.save(quote);
        assertThat(saved.getId()).isNotNull();

        final Quote found = quoteGateway.findById(saved.getId()).orElseThrow();
        assertThat(found.getProductId()).isEqualTo("prod-123");
        assertThat(found.getCoverages()).containsKey("Incendio");
        assertThat(found.getAssistances()).contains("Eletricista");
        assertThat(found.getCustomer().getName()).isEqualTo("John Wick");
    }
}
