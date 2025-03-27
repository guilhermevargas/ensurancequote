package br.com.itau.ensurancequote.gateways;

import br.com.itau.ensurancequote.gateways.clients.OfferClient;
import br.com.itau.ensurancequote.gateways.clients.dtos.OfferResponse;
import br.com.itau.ensurancequote.domains.models.MonthlyPremiumAmount;
import br.com.itau.ensurancequote.domains.models.Offer;
import org.springframework.stereotype.Component;

@Component
public class OfferGateway {

    private final OfferClient offerFeignClient;

    public OfferGateway(final OfferClient offerFeignClient) {
        this.offerFeignClient = offerFeignClient;
    }

    public Offer get(final String offerId) {
        final OfferResponse response = offerFeignClient.getOffer(offerId);
        if (response == null) {
            throw new RuntimeException("Offer not found for id: " + offerId);
        }
        return mapToDomain(response);
    }

    private Offer mapToDomain(final OfferResponse r) {
        return new Offer(
                r.id(),
                r.productId(),
                r.name(),
                r.createdAt(),
                r.active(),
                r.coverages(),
                r.assistances(),
                new MonthlyPremiumAmount(
                        r.monthlyPremiumAmount().minAmount(),
                        r.monthlyPremiumAmount().maxAmount(),
                        r.monthlyPremiumAmount().suggestedAmount())
        );
    }
}
