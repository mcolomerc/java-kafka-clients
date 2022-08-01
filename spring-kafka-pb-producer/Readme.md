# Spring Boot Kafka Protobuf Producer 

Spring Boot kafka producer that builds model from schemas registered at Schema Registry. 
Schema management is done by the Schema Owner, the producer only uses a predefined schema 
to publish a new schema by topic value. 

Default configurations:

 - TopicNameStrategy 
 - Auto register schemas (true) by the serializer 

## Why Spring Boot? 

The Spring Boot ecosystem is quite expanded in the Java World, and it is quite simple to develop a Kafka client 
with the ```spring-kafka``` Spring module. This demo app is not intended to explore who is the best Java Kafka
client and of course, it could be developed using any other language with a Kafka client and Schema Registry support. 

## Configuration 

```appliaction.properties```

```properties
spring.kafka.bootstrap-servers=localhost:9092 
spring.kafka.properties.schema.registry.url=http://localhost:8081

spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=io.confluent.kafka.serializers.protobuf.KafkaProtobufSerializer

persons.topic=sb-person --> topic name
persons.topic.replicas=1 --> Replicas
persons.topic.partitions=1 --> Partitions 

```

* Auto creation Topics: The producer will create the target topic if it not exists. 
* The schema with this configuration will be registered as: ```sp-person-value```


## Protobuf schemas 

1- Download Schema Registry .proto schemas configuring Schema Version and references.

2- Generate Java Model from Proto. 

```
mvn exec:exec
```

## Producer run

Never-ending loop 

```sh 
mvn spring-boot:run 
```

### Unit Test with ```Testcontainers``` 

```sh
mvn test
``` 