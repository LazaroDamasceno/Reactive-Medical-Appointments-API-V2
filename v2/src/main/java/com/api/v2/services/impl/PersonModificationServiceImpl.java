package com.api.v2.services.impl;

import com.api.v2.domain.Person;
import com.api.v2.domain.PersonAuditTrail;
import com.api.v2.domain.PersonAuditTrailRepository;
import com.api.v2.domain.PersonRepository;
import com.api.v2.dtos.PersonModificationDto;
import com.api.v2.services.PersonModificationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PersonModificationServiceImpl implements PersonModificationService {

    private final PersonRepository personRepository;
    private final PersonAuditTrailRepository personAuditTrailRepository;

    public PersonModificationServiceImpl(
            PersonRepository personRepository,
            PersonAuditTrailRepository personAuditTrailRepository
    ) {
        this.personRepository = personRepository;
        this.personAuditTrailRepository = personAuditTrailRepository;
    }

    @Override
    public Mono<Person> modify(@NotNull Person person, @Valid PersonModificationDto modificationDto) {
        return Mono.defer(() -> {
            PersonAuditTrail auditTrail = PersonAuditTrail.create(person);
            return personAuditTrailRepository
                    .save(auditTrail)
                    .then(Mono.defer(() -> {
                        person.modify(modificationDto);
                        return personRepository.save(person);
                    }));
        });
    }
}
