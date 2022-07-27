package com.mcolomer.kafka;

import com.github.javafaker.Faker;
import com.mcolomer.kafka.config.PersonProducerConfig;
import com.mcolomer.kafka.producer.PersonProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Locale;

@SpringBootApplication
public class ProducerApplication {

	private final Logger logger = LoggerFactory.getLogger(ProducerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ProducerApplication.class, args);
	}

	@Bean
	public Faker getFaker() {
		return new Faker(new Locale("en-EN"));
	}

	@Bean
	public CommandLineRunner CommandLineRunnerBean(PersonProducer personProducer, PersonProducerConfig config) {
		return (args) -> {
			logger.info("In CommandLineRunnerImpl ");

			for (String arg : args) {
				logger.debug(arg);
			}

			// kafkaClientProducer.sendMessage("--> Message from Spring boot");
			personProducer.sendPersons(config.getTopic(), config.getFakeNumber());

		};
	}

}
