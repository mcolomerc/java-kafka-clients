package com.mcolomer.kafka.producer;

import com.mcolomer.kafka.data.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonProducer {
    @Autowired
    private KafkaClientProducer producer;
    @Autowired
    private PersonService personService;

    public void sendPersons (String topic, int num) {
        for (int i=1; i<num; i++) {
           producer.sendMessage(topic, personService.getPerson());
        }
    }
    public void sendPerson (String topic) {
            producer.sendMessage(topic, personService.getPerson());
        }
    }


