package com.api.v2.doctors.dtos

import com.api.v2.people.dtos.PersonRegistrationDto

data class DoctorRegistrationDto(
    val medicalLicenseNumber: String,
    val personRegistrationDto: PersonRegistrationDto
)
