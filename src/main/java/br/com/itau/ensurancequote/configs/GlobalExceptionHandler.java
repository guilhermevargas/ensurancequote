package br.com.itau.ensurancequote.configs;

import br.com.itau.ensurancequote.domains.exceptions.ProductNotFoundException;
import br.com.itau.ensurancequote.domains.exceptions.QuoteAlreadyExistsException;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public String handleProductNotFoundException(final ProductNotFoundException ex) {
        logger.error(Arrays.toString(ex.getStackTrace()));
        return ex.getMessage();
    }

    @ExceptionHandler(QuoteAlreadyExistsException.class)
    @ResponseStatus(CONFLICT)
    public String handleQuoteAlreadyExistsException(final QuoteAlreadyExistsException ex) {
        logger.error(Arrays.toString(ex.getStackTrace()));
        return ex.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public String handleException(final Exception ex) {
        logger.error(Arrays.toString(ex.getStackTrace()));
        return "Something wrong happened!";
    }
}
