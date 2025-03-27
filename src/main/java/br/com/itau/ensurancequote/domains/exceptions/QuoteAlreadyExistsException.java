package br.com.itau.ensurancequote.domains.exceptions;

public class QuoteAlreadyExistsException extends RuntimeException {
    public QuoteAlreadyExistsException(final String message) {
        super(message);
    }
}
