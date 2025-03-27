package br.com.itau.ensurancequote.controllers.mappers;

import br.com.itau.ensurancequote.controllers.dtos.CreateQuoteRequest;
import br.com.itau.ensurancequote.controllers.dtos.CustomerQuote;
import br.com.itau.ensurancequote.controllers.dtos.QuoteResponse;
import br.com.itau.ensurancequote.domains.models.Customer;
import br.com.itau.ensurancequote.domains.models.Quote;

import java.time.LocalDateTime;

public class CustomerMapper {
    public static QuoteResponse mapTo(Quote quote) {
        return new QuoteResponse(
                quote.getId(),
                quote.getProductId(),
                quote.getOfferId(),
                quote.getCategory(),
                quote.getTotalMonthlyPremiumAmount(),
                quote.getTotalCoverageAmount(),
                quote.getCoverages(),
                quote.getAssistances(),
                mapTo(quote.getCustomer()),
                quote.getInsurancePolicyId(),
                quote.getCreatedAt(),
                quote.getUpdatedAt()
        );
    }

    private static CustomerQuote mapTo(Customer customer) {
        return new CustomerQuote(
                customer.getDocumentNumber(),
                customer.getName(),
                customer.getType(),
                customer.getGender(),
                customer.getDateOfBirth(),
                customer.getEmail(),
                customer.getPhoneNumber()
        );
    }

    public static Quote mapTo(final CreateQuoteRequest request) {
        return new Quote(
                null,
                request.productId(),
                request.offerId(),
                request.category(),
                request.totalMonthlyPremiumAmount(),
                request.totalCoverageAmount(),
                request.coverages(),
                request.assistances(),
                mapTo(request.customer()),
                null,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    public static Customer mapTo(final CustomerQuote customerQuote) {
        return new Customer(
                customerQuote.documentNumber(),
                customerQuote.name(),
                customerQuote.type(),
                customerQuote.gender(),
                customerQuote.dateOfBirth(),
                customerQuote.email(),
                customerQuote.phoneNumber()
        );
    }
}
