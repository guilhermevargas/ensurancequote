package br.com.itau.ensurancequote.exceptions;

import br.com.itau.ensurancequote.configs.GlobalExceptionHandler;
import br.com.itau.ensurancequote.domains.exceptions.ProductNotFoundException;
import br.com.itau.ensurancequote.domains.exceptions.QuoteAlreadyExistsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleProductNotFoundException_returnsUserFriendlyMessage() {
        ProductNotFoundException ex = new ProductNotFoundException("Product not found");
        String result = handler.handleProductNotFoundException(ex);
        assertEquals("Product not found", result);
    }

    @Test
    void handleQuoteAlreadyExistsException_returnsUserFriendlyMessage() {
        QuoteAlreadyExistsException ex = new QuoteAlreadyExistsException("Quote already exists");
        String result = handler.handleQuoteAlreadyExistsException(ex);
        assertEquals("Quote already exists", result);
    }

    @Test
    void handleGenericException_returnsGenericMessage() {
        Exception ex = new Exception("Unexpected error");
        String result = handler.handleException(ex);
        assertEquals("Something wrong happened!", result);
    }
}