package br.com.itau.ensurancequote.domains.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(final String message) {
        super(message);
    }
}
