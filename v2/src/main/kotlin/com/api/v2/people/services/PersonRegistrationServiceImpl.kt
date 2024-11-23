package com.api.v2.people.services

import com.api.v2.people.domain.Person
import com.api.v2.people.domain.PersonRepository
import com.api.v2.people.dtos.PersonRegistrationDto
import com.api.v2.people.exceptions.DuplicatedPersonalInformationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
class PersonRegistrationServiceImpl(
    private val personRepository: PersonRepository
): PersonRegistrationService {

    override suspend fun register(registrationDto: PersonRegistrationDto): Person {

        suspend fun handleDuplicatedSsn(ssn: String) {
            val isSsnDuplicated = personRepository
                .findAll()
                .filter { e -> e.ssn == registrationDto.ssn }
                .singleOrNull()
            val message = "The given SSN is already in use."
            if (isSsnDuplicated != null) throw DuplicatedPersonalInformationException(message)
        }

        suspend fun handleDuplicatedEmail(email: String) {
            val isEmailDuplicated = personRepository
                .findAll()
                .filter { e -> e.email == registrationDto.email }
                .singleOrNull()
            val message = "The given email is already in use."
            if (isEmailDuplicated != null) throw DuplicatedPersonalInformationException(message)
        }

        return withContext(Dispatchers.IO) {
            handleDuplicatedSsn(registrationDto.ssn)
            handleDuplicatedEmail(registrationDto.email)
            val person = Person.create(registrationDto)
            personRepository.save(person)
        }
    }

}