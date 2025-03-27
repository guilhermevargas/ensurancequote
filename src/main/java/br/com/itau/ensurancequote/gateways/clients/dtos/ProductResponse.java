package br.com.itau.ensurancequote.gateways.clients.dtos;

import java.time.LocalDateTime;
import java.util.List;

public record ProductResponse(
        String id,
        String name,
        LocalDateTime createdAt,
        boolean active,
        List<String> offers
) {}

