package br.com.itau.ensurancequote.gateways.repositories.entities;


import br.com.itau.ensurancequote.domains.models.Customer;
import br.com.itau.ensurancequote.domains.models.Quote;

public class QuoteEntityMapper {

    public QuoteEntity toEntity(final Quote quote) {
        QuoteEntity e = new QuoteEntity();
        e.setId(quote.getId());
        e.setProductId(quote.getProductId());
        e.setOfferId(quote.getOfferId());
        e.setCategory(quote.getCategory());
        e.setTotalMonthlyPremiumAmount(quote.getTotalMonthlyPremiumAmount());
        e.setTotalCoverageAmount(quote.getTotalCoverageAmount());
        e.setCoverages(quote.getCoverages());
        e.setAssistances(quote.getAssistances());
        e.setCustomer(toEmbedded(quote.getCustomer()));
        e.setInsurancePolicyId(quote.getInsurancePolicyId());
        e.setCreatedAt(quote.getCreatedAt());
        e.setUpdatedAt(quote.getUpdatedAt());
        return e;
    }

    public Quote toDomain(QuoteEntity e) {
        return new Quote(
                e.getId(),
                e.getProductId(),
                e.getOfferId(),
                e.getCategory(),
                e.getTotalMonthlyPremiumAmount(),
                e.getTotalCoverageAmount(),
                e.getCoverages(),
                e.getAssistances(),
                toDomainCustomer(e.getCustomer()),
                e.getInsurancePolicyId(),
                e.getCreatedAt(),
                e.getUpdatedAt()
        );
    }

    private CustomerEmbedded toEmbedded(Customer c) {
        if (c == null) return null;
        CustomerEmbedded emb = new CustomerEmbedded();
        emb.setDocumentNumber(c.getDocumentNumber());
        emb.setName(c.getName());
        emb.setType(c.getType());
        emb.setGender(c.getGender());
        emb.setDateOfBirth(c.getDateOfBirth());
        emb.setEmail(c.getEmail());
        emb.setPhoneNumber(c.getPhoneNumber());
        return emb;
    }

    private Customer toDomainCustomer(CustomerEmbedded emb) {
        if (emb == null) return null;
        return new Customer(
                emb.getDocumentNumber(),
                emb.getName(),
                emb.getType(),
                emb.getGender(),
                emb.getDateOfBirth(),
                emb.getEmail(),
                emb.getPhoneNumber()
        );
    }
}
