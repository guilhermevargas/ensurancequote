package br.com.itau.ensurancequote.controllers;

import br.com.itau.ensurancequote.controllers.dtos.CreateQuoteRequest;
import br.com.itau.ensurancequote.controllers.dtos.CustomerQuote;
import br.com.itau.ensurancequote.controllers.dtos.QuoteResponse;
import br.com.itau.ensurancequote.domains.models.Customer;
import br.com.itau.ensurancequote.domains.models.Quote;
import br.com.itau.ensurancequote.usecases.CreateQuote;
import br.com.itau.ensurancequote.usecases.FindQuote;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class QuoteControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private CreateQuote createQuote;

    @MockBean
    private FindQuote findQuote;

    private final Quote sampleQuote = new Quote(
            1L,
            "prod1",
            "offer1",
            "HOME",
            BigDecimal.valueOf(75.25),
            BigDecimal.valueOf(825000),
            Map.of("Incendio", BigDecimal.valueOf(250000)),
            Collections.singletonList("Encanador"),
            new Customer("36205578900", "John Doe", "NATURAL", "MALE", LocalDate.of(1973, 5, 2), "john@example.com", "1234567890"),
            null,
            LocalDateTime.now().minusMinutes(5),
            LocalDateTime.now()
    );

    @Test
    void createQuoteEndpointReturnsCreatedQuote() {
        final CustomerQuote customer = new CustomerQuote("36205578900", "John Doe", "NATURAL", "MALE", LocalDate.of(1973, 5, 2), "john@example.com", "1234567890");
        final CreateQuoteRequest request = new CreateQuoteRequest(
                "prod1",
                "offer1",
                "HOME",
                BigDecimal.valueOf(75.25),
                BigDecimal.valueOf(825000),
                Map.of("Incendio", BigDecimal.valueOf(250000)),
                Collections.singletonList("Encanador"),
                customer
        );
        when(createQuote.execute(any(Quote.class))).thenReturn(sampleQuote);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateQuoteRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<QuoteResponse> responseEntity = restTemplate.postForEntity("/quotes", entity, QuoteResponse.class);
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(201);
        QuoteResponse response = responseEntity.getBody();
        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(sampleQuote.getId());
        assertThat(response.productId()).isEqualTo(sampleQuote.getProductId());
        verify(createQuote).execute(any(Quote.class));
    }

    @Test
    void getQuoteEndpointReturnsQuote() {
        when(findQuote.execute(1L)).thenReturn(sampleQuote);
        ResponseEntity<QuoteResponse> responseEntity = restTemplate.getForEntity("/quotes/1", QuoteResponse.class);
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
        QuoteResponse response = responseEntity.getBody();
        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(sampleQuote.getId());
        verify(findQuote).execute(1L);
    }
}
