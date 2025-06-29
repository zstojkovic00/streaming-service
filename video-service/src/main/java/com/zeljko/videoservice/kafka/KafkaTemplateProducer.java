package com.zeljko.videoservice.kafka;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class KafkaTemplateProducer<T> {

    private final KafkaTemplate<String, T> kafkaTemplate;

    @Value("${spring.kafka.producer.timeout-millis:1000}")
    private int timeoutInMillis;

    private static final Logger log = LoggerFactory.getLogger(KafkaTemplateProducer.class);

    public KafkaTemplateProducer(KafkaTemplate<String, T> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    private CompletableFuture<SendResult<String, T>> sendMessageAsynchronous(String topic, String key, T message) {
        try {
            return kafkaTemplate.send(topic, key, message);
        } catch (Exception e) {
            log.error("Unknown error occurred while sending message: {} to topic: {}, error: {}",
                    message, topic, e.getMessage(), e);
            return CompletableFuture.failedFuture(e);
        }
    }

    private void sendMessageSynchronous(String topic, String key, T message) throws ExecutionException, InterruptedException, TimeoutException {
        kafkaTemplate.send(topic, key, message).get(timeoutInMillis, TimeUnit.MILLISECONDS);
    }
}
