package br.com.itau.ensurancequote.gateways.consumers;

import br.com.itau.ensurancequote.gateways.consumers.dtos.PolicyIssuedEvent;
import br.com.itau.ensurancequote.usecases.UpdateQuotePolicy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PolicyIssuedConsumerTest {

    @Mock
    private UpdateQuotePolicy updateQuotePolicy;

    @InjectMocks
    private PolicyIssuedConsumer consumer;

    @Test
    void onPolicyIssued_callsUpdateQuotePolicy() {
        PolicyIssuedEvent event = new PolicyIssuedEvent(123L, 456L);
        consumer.onPolicyIssued(event);
        verify(updateQuotePolicy).execute(123L, 456L);
    }
}
