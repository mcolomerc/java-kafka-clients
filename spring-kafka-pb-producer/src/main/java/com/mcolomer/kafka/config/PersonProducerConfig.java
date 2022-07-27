package com.mcolomer.kafka.config;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonProducerConfig {

    @Value(value = "${persons.topic}")
    private String topic;

    @Value (value = "${persons.fake.number}")
    private int numPersons;

    public String getTopic () {
        return this.topic;
    }

    public int getFakeNumber () {
        return this.numPersons;
    }
}
