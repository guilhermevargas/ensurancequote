package br.com.itau.ensurancequote.gateways.clients;

import br.com.itau.ensurancequote.gateways.clients.dtos.OfferResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "offerClient", url = "${catalog.service.url}")
public interface OfferClient {

    @GetMapping("/offers/{offerId}")
    OfferResponse getOffer(@PathVariable("offerId") String offerId);
}
