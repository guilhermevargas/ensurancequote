package br.com.itau.ensurancequote.gateways.producers;

import java.util.concurrent.ExecutionException;

public interface MessageProducer {
    void send(final Object body, final String topic);
}
