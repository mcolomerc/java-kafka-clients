package com.mcolomer.kafka.data;

import com.mcolomer.model.PersonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repo;
    public PersonBuilder.Person getPerson () {
        return repo.Build();
    }
}
