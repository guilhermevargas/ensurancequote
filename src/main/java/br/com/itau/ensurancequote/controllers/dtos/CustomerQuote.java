package br.com.itau.ensurancequote.controllers.dtos;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CustomerQuote(
        @NotNull
        String documentNumber,
        String name,
        String type,
        String gender,
        LocalDate dateOfBirth,
        String email,
        String phoneNumber
) {}