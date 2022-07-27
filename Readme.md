# Java Kafka Clients demo

## UP 

* Zookeeper 
* Kafka Broker 
* Schema Registry 
* Confluent Control Center 

```sh
docker-compose up -d
```

## Spring Boot Kafka Clients 

### Spring Boot Kafka Protobuf Producer

./spring-kafka-pb-producer

* Generate source from .proto definition

* Generate protobuf messages: io.confluent.kafka.serializers.protobuf.KafkaProtobufSerializer 

* Schema registry integration

* Person data generator

 
### Spring Boot Kafka Protobuf Consumer

./spring-kafka-pb-consumer 

* io.confluent.kafka.serializers.protobuf.KafkaProtobufDeserializer

* Schema registry integration

* Consume Generic type: com.google.protobuf.DynamicMessage



 