package br.com.itau.ensurancequote.gateways.consumers.dtos;

public record PolicyIssuedEvent(Long quoteId, Long policyNumber) {}
