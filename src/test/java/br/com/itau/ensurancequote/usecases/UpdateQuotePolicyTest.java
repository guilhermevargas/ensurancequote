package br.com.itau.ensurancequote.usecases;

import br.com.itau.ensurancequote.gateways.QuoteGateway;
import br.com.itau.ensurancequote.domains.models.Customer;
import br.com.itau.ensurancequote.domains.models.Quote;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateQuotePolicyTest {
    @Mock
    private QuoteGateway quoteRepository;

    @InjectMocks
    private UpdateQuotePolicy useCase;

    @Test
    void shouldUpdateQuoteWithPolicyNumber() {
        final Long quoteId = 1L;
        final Long policyNumber = 555L;
        final LocalDateTime createdAt = LocalDateTime.now().minusDays(1);
        final LocalDateTime oldUpdatedAt = LocalDateTime.now().minusDays(1);
        final Quote existingQuote = createQuote(quoteId, createdAt, oldUpdatedAt);

        when(quoteRepository.findById(quoteId)).thenReturn(Optional.of(existingQuote));
        when(quoteRepository.save(any(Quote.class))).thenAnswer(invocation -> invocation.getArgument(0));

        useCase.execute(quoteId, policyNumber);

        verify(quoteRepository).findById(quoteId);
        verify(quoteRepository).save(existingQuote.assignPolicy(policyNumber));
    }

    private Quote createQuote(final Long quoteId, final LocalDateTime createdAt, final LocalDateTime oldUpdatedAt) {
        return new Quote(
                quoteId,
                "prod1",
                "offer1",
                "HOME",
                BigDecimal.valueOf(75.25),
                BigDecimal.valueOf(825000),
                Map.of("Incendio", BigDecimal.valueOf(250000)),
                Collections.singletonList("Encanador"),
                new Customer("36205578900", "John Doe", "NATURAL", "MALE",
                        LocalDate.of(1973, 5, 2), "john@example.com", "1234567890"),
                null,
                createdAt,
                oldUpdatedAt
        );
    }

    @Test
    void shouldThrowIfQuoteNotFound() {
        Long quoteId = 1L;
        Long policyNumber = 555L;
        when(quoteRepository.findById(quoteId)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> useCase.execute(quoteId, policyNumber));
        verify(quoteRepository, never()).save(any(Quote.class));
    }

}