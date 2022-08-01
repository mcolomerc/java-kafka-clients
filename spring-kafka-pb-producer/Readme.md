# Spring Boot Kafka Protobuf Producer 

Spring Boot kafka producer that builds model from schemas registered at Schema Registry. 
Schema management is done by the Schema Owner, the producer only uses a predefined schema 
to publish a new schema by topic value. 

Default configurations:

 - TopicNameStrategy 
 - Auto register schemas by the serializer 

## Configuration 

```appliaction.properties```

```properties


```

## Protobuf schemas 

1- Download Schema Registry .proto schemas configuring Schema Version and references.

2- Generate Java Model from Proto. 

```
mvn exec:exec
```

## Producer run

```sh 
mvn spring-boot:run 
```

Test 

```sh
mvn test
``` 