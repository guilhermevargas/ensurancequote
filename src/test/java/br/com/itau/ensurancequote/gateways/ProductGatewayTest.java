package br.com.itau.ensurancequote.gateways;

import br.com.itau.ensurancequote.domains.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureWireMock
class ProductGatewayTest {
    @Autowired
    private ProductGateway productGateway;

    @Test
    void shouldReturnStubbedProduct() {
        Product response = productGateway.get("1b2da7cc-b367-4196-8a78-9cfeec21f587");
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo("1b2da7cc-b367-4196-8a78-9cfeec21f587");
        assertThat(response.getName()).isEqualTo("Seguro de Vida");
        assertThat(response.isActive()).isTrue();
        assertThat(response.getOffers()).containsExactly(
                "adc56d77-348c-4bf0-908f-22d402ee715c",
                "bdc56d77-348c-4bf0-908f-22d402ee715c",
                "cdc56d77-348c-4bf0-908f-22d402ee715c"
        );
    }
}