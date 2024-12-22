package com.api.v2.people.services.impl;

import com.api.v2.people.domain.Person;
import com.api.v2.people.domain.PersonRepository;
import com.api.v2.people.dtos.PersonRegistrationDto;
import com.api.v2.people.events.PersonRegistrationEvent;
import com.api.v2.people.services.PersonRegistrationService;
import jakarta.validation.Valid;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PersonRegistrationServiceImpl implements PersonRegistrationService {

    private final PersonRepository personRepository;
    private final ApplicationEventPublisher eventPublisher;

    public PersonRegistrationServiceImpl(
            PersonRepository personRepository,
            ApplicationEventPublisher eventPublisher
    ) {
        this.personRepository = personRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Mono<Person> register(@Valid PersonRegistrationDto registrationDto) {
        return Mono.defer(() -> {
            Person person = Person.create(registrationDto);
            eventPublisher.publishEvent(PersonRegistrationEvent.create(person));
            return personRepository.save(person);
        });
    }
}
