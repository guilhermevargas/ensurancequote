package br.com.itau.ensurancequote.domains.models;

import java.time.LocalDate;
import java.util.Objects;

public final class Customer {

    private final String documentNumber;
    private final String name;
    private final String type;
    private final String gender;
    private final LocalDate dateOfBirth;
    private final String email;
    private final String phoneNumber;

    public Customer(
            String documentNumber,
            String name,
            String type,
            String gender,
            LocalDate dateOfBirth,
            String email,
            String phoneNumber) {
        this.documentNumber = documentNumber;
        this.name = name;
        this.type = type;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getGender() {
        return gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;
        return Objects.equals(documentNumber, customer.documentNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentNumber);
    }
}

