package com.api.v2.services.impl;

import com.api.v2.domain.Person;
import com.api.v2.domain.PersonRepository;
import com.api.v2.dtos.PersonRegistrationDto;
import com.api.v2.services.PersonRegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PersonRegistrationServiceImpl implements PersonRegistrationService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Mono<Person> register(@Valid PersonRegistrationDto registrationDto) {
        return Mono.defer(() -> {
            Person person = Person.create(registrationDto);
            return personRepository.save(person);
        });
    }
}
