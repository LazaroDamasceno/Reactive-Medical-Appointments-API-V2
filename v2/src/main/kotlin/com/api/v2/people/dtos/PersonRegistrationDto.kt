package com.api.v2.people.dtos

data class PersonRegistrationDto(
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val ssn: String,
    val birthDate: String,
    val email: String,
    val phoneNumber: String
)
