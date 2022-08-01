package com.mcolomer.kafka;

import com.mcolomer.kafka.producer.PersonProducer;
import com.mcolomer.model.PersonBuilder;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Collections;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = { TestConfig.class })
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
class ProducerEmbTest {

    @Autowired
    private PersonProducer personProducer;

    @Value("${persons.topic}")
    private String topic;

    @Value("${persons.fake.number}")
    private int num;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Test
    public void givenEmbeddedKafkaBroker_whenSendingWithSimpleProducer_thenMessageReceived()
            throws Exception {
        try {
            personProducer.sendPersons(topic, num);
            Consumer<String, PersonBuilder.Person> consumer = configureConsumer();
            ConsumerRecord<String, PersonBuilder.Person> singleRecord = KafkaTestUtils.getSingleRecord(consumer, topic);
            assertThat(singleRecord).isNotNull();
            consumer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private Consumer<String, PersonBuilder.Person> configureConsumer() {
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("testGroup", "true", embeddedKafkaBroker);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        Consumer<String, PersonBuilder.Person> consumer = new DefaultKafkaConsumerFactory<String, PersonBuilder.Person>(consumerProps)
                .createConsumer();
        consumer.subscribe(Collections.singleton(topic));
        return consumer;
    }
}