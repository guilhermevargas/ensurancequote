package br.com.itau.ensurancequote.gateways.consumers;

import br.com.itau.ensurancequote.gateways.consumers.dtos.PolicyIssuedEvent;
import br.com.itau.ensurancequote.usecases.UpdateQuotePolicy;
import org.slf4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class PolicyIssuedConsumer {
    private static final Logger LOGGER = getLogger(PolicyIssuedConsumer.class);
    private final UpdateQuotePolicy updateQuotePolicy;

    public PolicyIssuedConsumer(UpdateQuotePolicy updateQuotePolicy) {
        this.updateQuotePolicy = updateQuotePolicy;
    }

    @KafkaListener(topics = "${policy.issued.topic}", groupId = "quote-service-group")
    public void onPolicyIssued(final PolicyIssuedEvent event) {
        LOGGER.info("Consuming policy issued payload='{}' to topic='{}'", event, event);
        updateQuotePolicy.execute(event.quoteId(), event.policyNumber());
    }
}
