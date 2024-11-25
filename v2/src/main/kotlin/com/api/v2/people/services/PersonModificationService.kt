package com.api.v2.people.services

import com.api.v2.people.domain.Person
import com.api.v2.people.dtos.PersonModificationDto

interface PersonModificationService {
    suspend fun modify(person: Person, modificationDto: PersonModificationDto): Person
}