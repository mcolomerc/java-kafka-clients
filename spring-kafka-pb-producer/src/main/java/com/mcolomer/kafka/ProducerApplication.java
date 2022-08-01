package com.mcolomer.kafka;

import com.github.javafaker.Faker;
import com.mcolomer.kafka.config.PersonProducerConfig;
import com.mcolomer.kafka.producer.PersonProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.Locale;

@SpringBootApplication
public class ProducerApplication {

	private final Logger logger = LoggerFactory.getLogger(ProducerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ProducerApplication.class, args);
	}



	@Bean
	@Profile("! test")
	public ApplicationRunner runner(PersonProducer personProducer, PersonProducerConfig config) {
		return args -> {
			logger.info("Producing messages. . . ");
			for (;;) {
				personProducer.sendPerson(config.getTopic());
			}
		};
	}

}
