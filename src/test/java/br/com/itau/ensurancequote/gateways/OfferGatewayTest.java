package br.com.itau.ensurancequote.gateways;

import br.com.itau.ensurancequote.domains.models.Offer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@AutoConfigureWireMock
public class OfferGatewayTest {

    @Autowired
    private OfferGateway offerGateway;

    @Test
    void shouldReturnOfferUsingYamlStub() {
        final Offer offer = offerGateway.get("adc56d77-348c-4bf0-908f-22d402ee715c");

        assertThat(offer.getId()).isEqualTo("adc56d77-348c-4bf0-908f-22d402ee715c");
        assertThat(offer.getProductId()).isEqualTo("1b2da7cc-b367-4196-8a78-9cfeec21f587");
        assertThat(offer.getName()).isEqualTo("Seguro de Vida Familiar");
        assertThat(offer.getCreatedAt()).isEqualTo(LocalDateTime.parse("2021-07-01T00:00:00"));
        assertThat(offer.isActive()).isTrue();
        assertThat(offer.getCoverages().get("Incêndio")).isEqualByComparingTo(BigDecimal.valueOf(500000.00));
        assertThat(offer.getAssistances())
                .containsExactly("Encanador", "Eletricista", "Chaveiro 24h", "Assistência Funerária");
        assertThat(offer.getMonthlyPremiumAmount().getMaxAmount()).isEqualByComparingTo(BigDecimal.valueOf(100.74));
        assertThat(offer.getMonthlyPremiumAmount().getMinAmount()).isEqualByComparingTo(BigDecimal.valueOf(50.00));
        assertThat(offer.getMonthlyPremiumAmount().getSuggestedAmount()).isEqualByComparingTo(BigDecimal.valueOf(60.25));
    }
}