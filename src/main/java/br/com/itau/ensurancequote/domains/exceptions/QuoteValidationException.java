package br.com.itau.ensurancequote.domains.exceptions;

public class QuoteValidationException extends RuntimeException {
    public QuoteValidationException(String message) {
        super(message);
    }
}
