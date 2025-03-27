package br.com.itau.ensurancequote.gateways.producers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer implements MessageProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducer(final KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(final Object event, final String topic) {
        LOGGER.info("Event is sending with payload='{}' to topic='{}'", event, topic);
        kafkaTemplate.send(topic, "test", event);
        LOGGER.info("Event was send with payload='{}' to topic='{}'", event, topic);
    }
}
