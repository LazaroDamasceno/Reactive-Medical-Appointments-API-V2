package com.api.v2.people.services

import com.api.v2.people.dtos.PersonRegistrationDto

interface PersonRegistrationService {
    suspend fun register(registrationDto: PersonRegistrationDto)
}