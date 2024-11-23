package com.api.v2.people.dtos

import java.time.LocalDate

data class PersonRegistrationDto(
    val fullNameDto: PersonFullNameDto,
    val ssn: String,
    val birthDate: LocalDate,
    val email: String,
    val phoneNumber: String
)
