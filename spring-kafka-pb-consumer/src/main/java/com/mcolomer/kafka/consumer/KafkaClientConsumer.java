package com.mcolomer.kafka.consumer;

import com.google.protobuf.DynamicMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaClientConsumer {
    private final Logger logger = LoggerFactory.getLogger(KafkaClientConsumer.class);

    @KafkaListener(topics = "${persons.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenGroupFoo(DynamicMessage message) {
        logger.info("Received Message in group foo: " + message);
    }
}
