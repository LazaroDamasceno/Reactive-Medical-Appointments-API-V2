package com.api.v2.people.services.interfaces;

import com.api.v2.people.domain.exposed.Person;
import com.api.v2.people.dtos.PersonRegistrationDto;
import reactor.core.publisher.Mono;

public interface PersonRegistrationService {
    Mono<Person> register(PersonRegistrationDto registrationDto);
}
