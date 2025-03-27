package br.com.itau.ensurancequote.gateways.clients;

import br.com.itau.ensurancequote.gateways.clients.dtos.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(
        name = "productClient",
        url = "${catalog.service.url}"
)
public interface ProductClient {

    @GetMapping("/products/{productId}")
    ProductResponse get(@PathVariable("productId") String productId);
}
