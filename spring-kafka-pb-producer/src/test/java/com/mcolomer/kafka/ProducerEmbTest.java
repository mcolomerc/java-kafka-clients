package com.mcolomer.kafka;

import com.google.protobuf.DynamicMessage;
import com.mcolomer.kafka.producer.PersonProducer;

import io.confluent.kafka.serializers.protobuf.KafkaProtobufDeserializer;
import io.confluent.kafka.serializers.protobuf.KafkaProtobufDeserializerConfig;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicListing;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.util.Collections.singleton;
import static org.apache.kafka.clients.consumer.ConsumerConfig.*;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class ProducerEmbTest {

    private static final String TEST_TOPIC = "test-topic";

    @Autowired
    private PersonProducer personProducer;

    @Autowired
    private KafkaAdmin admin;

    @Container public static KafkaContainer kafkaContainer =  new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.2.0"));

    @Configuration
    @Import({ProducerApplication.class, MockRegistryBeans.class})
    public static class Beans {
        @Bean
        NewTopic testTopic() {
            return new NewTopic(TEST_TOPIC, 1, (short) 1);
        }
    }
    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }

    protected Map<String, Object> getKafkaConsumerConfiguration() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaContainer.getBootstrapServers());
        configs.put(GROUP_ID_CONFIG, "testGroup");
        configs.put(AUTO_OFFSET_RESET_CONFIG, "earliest");
        configs.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        configs.put(VALUE_DESERIALIZER_CLASS_CONFIG, KafkaProtobufDeserializer.class.getName());
        configs.put (KafkaProtobufDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://mock.com:8081");
       // configs.put (KafkaProtobufDeserializerConfig.SPECIFIC_PROTOBUF_VALUE_TYPE, PersonBuilder.Person.class.getName());
        return configs;
    }

    @Autowired
    KafkaListenerEndpointRegistry registry;

    @Test
    void contextLoads() {
        waitForAssignment();
    }

    @Test
    public void testCreationOfTopicAtStartup() throws IOException, InterruptedException, ExecutionException {
        AdminClient client = AdminClient.create(admin.getConfigurationProperties());
        Collection<TopicListing> topicList = client.listTopics().listings().get();
        assertNotNull(topicList);
        assertEquals(topicList.stream().map(l -> l.name()).collect(Collectors.toList()), Arrays.asList(TEST_TOPIC));
    }

    @Test
    public void testProducer () {
        waitForAssignment();
        personProducer.sendPerson(TEST_TOPIC);
        try {
            KafkaConsumer<Integer, DynamicMessage> consumer = new KafkaConsumer(getKafkaConsumerConfiguration());
            consumer.subscribe(Collections.singletonList(TEST_TOPIC));
            await().atMost(10, TimeUnit.SECONDS).until(() -> {
                ConsumerRecords<Integer, DynamicMessage> records = consumer.poll(Duration.ofMillis(100));
                if (records.isEmpty()) {
                    return false;
                }
                records.forEach(r -> System.out.println(r.topic() + " *** " + r.key() + " *** " + r.value()));
                Assertions.assertThat(records.count()).isEqualTo(1);
                return true;
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void waitForAssignment() {
        registry
                .getListenerContainers()
                .forEach(container -> ContainerTestUtils.waitForAssignment(container, 1));
    }

}
