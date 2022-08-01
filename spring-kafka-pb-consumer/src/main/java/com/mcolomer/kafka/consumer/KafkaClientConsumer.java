package com.mcolomer.kafka.consumer;

import com.google.protobuf.Descriptors;
import com.google.protobuf.DynamicMessage;

import com.mcolomer.model.PersonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class KafkaClientConsumer {
    private final Logger logger = LoggerFactory.getLogger(KafkaClientConsumer.class);

    @KafkaListener(topics = "${persons.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenPersons(PersonBuilder.Person person) {
        logger.info("Received Person: {} ",  person);
        logger.info("Received Person - Address - City: {} ",  person.getAddress().getCity());
    }
}
