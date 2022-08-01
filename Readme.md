# Java Kafka Clients demo

## UP 

* Zookeeper 
* Kafka Broker 
* Schema Registry 
* Confluent Control Center 

```sh
docker-compose up -d
```


## Schema Owner 

./spring-kafka-pb-owner

Register schemas with the Schema Registry server using the Maven Plugin 

```xml
<groupId>io.confluent</groupId>
<artifactId>kafka-schema-registry-maven-plugin</artifactId>
```

![owner](docs/schema-catalog.png)


```sh
mvn schema-registry:register
``` 

[More](spring-kafka-pb-owner/Readme.md)


## Spring Boot Kafka Clients  

### Spring Boot Kafka Protobuf Producer

Folder: ```./spring-kafka-pb-producer```

* Generate source from .proto definition

* Generate protobuf messages: io.confluent.kafka.serializers.protobuf.KafkaProtobufSerializer   

* Person data generator

* Produce Persons

[More](spring-kafka-pb-producer/Readme.md)

 
### Spring Boot Kafka Protobuf Consumer

Folder: ```./spring-kafka-pb-consumer``` 

* io.confluent.kafka.serializers.protobuf.KafkaProtobufDeserializer

* Consume Persons from Kafka  

[More](spring-kafka-pb-consumer/Readme.md)



 