package br.com.itau.ensurancequote.usecases;

import br.com.itau.ensurancequote.domains.exceptions.ProductNotFoundException;
import br.com.itau.ensurancequote.gateways.QuoteGateway;
import br.com.itau.ensurancequote.domains.models.Quote;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindQuoteTest {

    @Mock
    private QuoteGateway quoteGateway;

    @InjectMocks
    private FindQuote findQuote;

    @Test
    void executeReturnsQuoteWhenFound() {
        final Long quoteId = 1L;
        final Quote quote = new Quote(
                quoteId,
                "prod1",
                "offer1",
                "HOME",
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(5000),
                Map.of("Coverage1", BigDecimal.valueOf(1000)),
                null,
                null,
                null,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        when(quoteGateway.findById(quoteId)).thenReturn(Optional.of(quote));
        Quote result = findQuote.execute(quoteId);
        assertNotNull(result);
        assertEquals(quoteId, result.getId());
    }

    @Test
    void executeThrowsExceptionWhenQuoteNotFound() {
        Long quoteId = 1L;
        when(quoteGateway.findById(quoteId)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> findQuote.execute(quoteId));
    }
}
