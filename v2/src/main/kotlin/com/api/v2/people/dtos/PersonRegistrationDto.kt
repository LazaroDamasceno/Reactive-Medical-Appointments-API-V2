package com.api.v2.people.dtos

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDate

data class PersonRegistrationDto(
    val firstName: @NotBlank String,
    val middleName: String,
    val lastName: @NotBlank String,
    val ssn: String,
    val birthDate: @NotNull LocalDate,
    val email: @Email @NotBlank String,
    val phoneNumber: @NotBlank @Size(min=10, max=10) String
)
