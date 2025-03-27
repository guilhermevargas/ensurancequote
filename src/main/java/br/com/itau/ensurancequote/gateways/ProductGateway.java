package br.com.itau.ensurancequote.gateways;

import br.com.itau.ensurancequote.domains.exceptions.ProductNotFoundException;
import br.com.itau.ensurancequote.gateways.clients.ProductClient;
import br.com.itau.ensurancequote.gateways.clients.dtos.ProductResponse;
import br.com.itau.ensurancequote.domains.models.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductGateway {
    private final ProductClient productClient;

    public ProductGateway(final ProductClient productClient) {
        this.productClient = productClient;
    }

    public Product get(final String productId) {
        final ProductResponse response = productClient.get(productId);
        if (response == null) {
            throw new ProductNotFoundException("Product not found for ID: " + productId);
        }
        return mapToDomain(response);
    }

    private Product mapToDomain(ProductResponse resp) {
        return new Product(
                resp.id(),
                resp.name(),
                resp.createdAt(),
                resp.active(),
                resp.offers()
        );
    }
}
