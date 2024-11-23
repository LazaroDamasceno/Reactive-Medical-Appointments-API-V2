package com.api.v2.people.dtos

import jakarta.validation.constraints.NotBlank

data class PersonFullNameDto(
    val firstName: @NotBlank String,
    val middleName: String,
    val lastName: @NotBlank String
)
