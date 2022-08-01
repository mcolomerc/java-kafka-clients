package com.mcolomer.kafka.config;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class PersonProducerConfig {

    @Value(value = "${persons.topic}")
    private String topic;

    public String getTopic () {
        return this.topic;
    }

    @Bean
    public Faker getFaker() {
        return new Faker(new Locale("en-EN"));
    }

}
