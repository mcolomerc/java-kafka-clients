package com.mcolomer.kafka.producer;

import com.mcolomer.model.PersonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class KafkaClientProducer {

    private static final Logger log = LoggerFactory.getLogger(KafkaClientProducer.class);

    @Autowired
    private KafkaTemplate<String, PersonBuilder.Person> protoTemplate;


    public void sendMessage(String topic, PersonBuilder.Person person) {
        ListenableFuture<SendResult<String, PersonBuilder.Person>> future = protoTemplate.send(topic, person);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, PersonBuilder.Person> result) {
                log.info("Message has been sent. Offset: {} ", result.getRecordMetadata().offset());
            }
            @Override
            public void onFailure(Throwable ex) {
                log.error("Something went wrong with the message {} ", person.toString());
                log.error (ex.getMessage());
            }
        });
    }

}

