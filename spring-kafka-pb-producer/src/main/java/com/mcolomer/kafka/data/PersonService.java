package com.mcolomer.kafka.data;

import com.github.javafaker.Faker;

import com.mcolomer.model.AddressBuilder;

import com.mcolomer.model.PersonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private Faker faker;


    public PersonBuilder.Person Build () {
        return PersonBuilder.Person.newBuilder()
                .setName(faker.name().fullName())
                .setEmail(faker.name().lastName() + "@mail.com")
                .setAge(faker.number().randomDigit())
                .setCompany(faker.company().name())
                .setGender(faker.demographic().sex())
                .setJob(faker.company().profession())
                .setPhone(faker.phoneNumber().phoneNumber())
                .setAddress(
                        AddressBuilder.Address.newBuilder()
                                .setStreet(faker.address().streetName())
                                .setNumber(faker.address().streetAddressNumber().toString())
                                .setCity(faker.address().cityName())
                                .setCountry(faker.address().country())
                                .build()
                )
                .build();

    }
}
