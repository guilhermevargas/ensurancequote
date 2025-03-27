package br.com.itau.ensurancequote.gateways.producers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class KafkaProducerTest {

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Test
    void shouldSendEventToKafka() {
        KafkaProducer producer = new KafkaProducer(kafkaTemplate);
        String topic = "test-topic";
        String event = "Sample Event";

        producer.send(event, topic);

        ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Object> eventCaptor = ArgumentCaptor.forClass(Object.class);

        verify(kafkaTemplate).send(topicCaptor.capture(), keyCaptor.capture(), eventCaptor.capture());
        assertThat(topicCaptor.getValue()).isEqualTo(topic);
        assertThat(keyCaptor.getValue()).isEqualTo("test");
        assertThat(eventCaptor.getValue()).isEqualTo(event);
    }
}
