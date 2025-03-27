package br.com.itau.ensurancequote.controllers;

import br.com.itau.ensurancequote.controllers.dtos.CreateQuoteRequest;
import br.com.itau.ensurancequote.controllers.dtos.QuoteResponse;
import br.com.itau.ensurancequote.controllers.mappers.CustomerMapper;
import br.com.itau.ensurancequote.domains.models.Quote;
import br.com.itau.ensurancequote.usecases.CreateQuote;
import br.com.itau.ensurancequote.usecases.FindQuote;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static br.com.itau.ensurancequote.controllers.mappers.CustomerMapper.mapTo;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/quotes")
public class QuoteController {
    private final CreateQuote createQuote;
    private final FindQuote findQuote;

    private static final Logger logger = getLogger(QuoteController.class);

    public QuoteController(final CreateQuote createQuote, final FindQuote findQuote) {
        this.createQuote = createQuote;
        this.findQuote = findQuote;
    }

    @PostMapping
    public ResponseEntity<QuoteResponse> createQuote(@RequestBody CreateQuoteRequest request) {
        logger.info("Received Post request for product: {} offer: {} document number: {} ",
                request.productId(), request.offerId(), request.customer().documentNumber());

        final Quote quote = CustomerMapper.mapTo(request);
        final Quote savedQuote = createQuote.execute(quote);

        logger.info("Returning quote with id: {}", quote.getId());

        return ResponseEntity.status(CREATED).body(mapTo(savedQuote));
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuoteResponse> getQuote(@PathVariable Long id) {
        logger.info("Received GET request for quote with id: {}", id);

        final Quote quote = findQuote.execute(id);
        final QuoteResponse response = mapTo(quote);

        logger.info("Returning quote with id: {}", response.id());

        return ResponseEntity.ok(response);
    }
}
