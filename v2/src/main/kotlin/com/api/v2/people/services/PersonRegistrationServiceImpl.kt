package com.api.v2.people.services

import com.api.v2.people.domain.Person
import com.api.v2.people.domain.PersonRepository
import com.api.v2.people.dtos.PersonRegistrationDto
import com.api.v2.people.exceptions.DuplicatedPersonalInformationException
import jakarta.validation.Valid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
internal class PersonRegistrationServiceImpl(
    private val personRepository: PersonRepository
): PersonRegistrationService {

    override suspend fun register(registrationDto: @Valid PersonRegistrationDto): Person {

        suspend fun handleDuplicatedSsn() {
            val isSsnDuplicated = personRepository
                .findAll()
                .filter { e -> e.ssn == registrationDto.ssn }
                .singleOrNull() != null
            val message = "The given SSN is already in use."
            if (isSsnDuplicated) throw DuplicatedPersonalInformationException(message)
        }

        suspend fun handleDuplicatedEmail() {
            val isEmailDuplicated = personRepository
                .findAll()
                .filter { e -> e.email == registrationDto.email }
                .singleOrNull() != null
            val message = "The given email is already in use."
            if (isEmailDuplicated) throw DuplicatedPersonalInformationException(message)
        }

        return withContext(Dispatchers.IO) {
            handleDuplicatedSsn()
            handleDuplicatedEmail()
            val person = Person.create(registrationDto)
            personRepository.save(person)
        }
    }

}