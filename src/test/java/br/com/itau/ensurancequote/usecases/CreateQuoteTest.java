package br.com.itau.ensurancequote.usecases;

import br.com.itau.ensurancequote.domains.exceptions.QuoteAlreadyExistsException;
import br.com.itau.ensurancequote.gateways.OfferGateway;
import br.com.itau.ensurancequote.gateways.ProductGateway;
import br.com.itau.ensurancequote.gateways.QuoteGateway;
import br.com.itau.ensurancequote.gateways.producers.MessageProducer;
import br.com.itau.ensurancequote.gateways.producers.dtos.QuoteReceivedEvent;
import br.com.itau.ensurancequote.domains.models.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.math.BigDecimal.valueOf;
import static java.time.LocalDate.of;
import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateQuoteTest {

    @Mock
    private QuoteGateway quoteGateway;
    @Mock
    private ProductGateway productGateway;
    @Mock
    private OfferGateway offerGateway;
    @Mock
    private MessageProducer messageProducer;

    @Value("${quote.received.topic}")
    private String quoteReceivedTopic;

    @InjectMocks
    private CreateQuote createQuote;

    @Test
    void shouldExecuteAllSteps_whenQuoteIsValid() {
        final Quote quote = createQuote();
        final Product product = createProduct();
        final Offer offer = createOff();

        when(productGateway.get("prod1")).thenReturn(product);
        when(offerGateway.get("offer1")).thenReturn(offer);

        when(quoteGateway.save(quote)).thenReturn(
                new Quote(
                        1L,
                        quote.getProductId(),
                        quote.getOfferId(),
                        quote.getCategory(),
                        quote.getTotalMonthlyPremiumAmount(),
                        quote.getTotalCoverageAmount(),
                        quote.getCoverages(),
                        quote.getAssistances(),
                        quote.getCustomer(),
                        quote.getInsurancePolicyId(),
                        quote.getCreatedAt(),
                        quote.getUpdatedAt()
                )
        );

        createQuote.execute(quote);

        verify(productGateway).get("prod1");
        verify(offerGateway).get("offer1");
        verify(quoteGateway).save(quote);
        verify(messageProducer).send(any(QuoteReceivedEvent.class), eq(quoteReceivedTopic));
    }

    @Test
    void shouldThrowQuoteAlreadyExistsExceptionWhenSaveFailsDueToUniqueConstraint() {
        final Quote quote = createQuote();
        final Product product = createProduct();
        final Offer offer = createOff();

        when(productGateway.get("prod1")).thenReturn(product);
        when(offerGateway.get("offer1")).thenReturn(offer);
        when(quoteGateway.save(quote)).thenThrow(new DataIntegrityViolationException("Unique constraint violation"));

        QuoteAlreadyExistsException ex = assertThrows(QuoteAlreadyExistsException.class, () -> createQuote.execute(quote));
        assertEquals("A quote with the given product, offer, and customer already exists.", ex.getMessage());
    }

    private static Offer createOff() {
        return new Offer(
                "offer1",
                "p1",
                "Seguro Casa",
                LocalDateTime.now(),
                true,
                Map.of(
                        "Incendio", BigDecimal.valueOf(300000),
                        "Desastres naturais", BigDecimal.valueOf(400000)
                ),
                List.of("Encanador", "Assist2"),
                new MonthlyPremiumAmount(BigDecimal.valueOf(50), BigDecimal.valueOf(100), BigDecimal.valueOf(60))
        );
    }

    private static Product createProduct() {
        return new Product(
                "p1",
                "Seguro Residencial",
                LocalDateTime.now(),
                true,
                List.of("offer1")
        );
    }

    private static Quote createQuote() {
        return new Quote(
                null,
                "prod1",
                "offer1",
                "HOME",
                valueOf(75.25),
                valueOf(250000),
                Map.of("Incendio", valueOf(250000)),
                Collections.singletonList("Encanador"),
                new Customer("36205578900", "John Doe", "NATURAL", "MALE",
                        of(1973, 5, 2), "john@example.com", "1234567890"),
                null,
                now(),
                now()
        );
    }

}