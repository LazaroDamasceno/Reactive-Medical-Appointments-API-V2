package com.api.v2.people.services

import com.api.v2.people.domain.Person
import com.api.v2.people.domain.PersonRepository
import com.api.v2.people.dtos.PersonModificationDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class PersonModificationServiceImpl(
    private val personRepository: PersonRepository
): PersonModificationService {
    override suspend fun modify(person: Person, modificationDto: PersonModificationDto): Person {
        return withContext(Dispatchers.IO) {
            person.modify(modificationDto)
            personRepository.save(person)
        }
    }
}