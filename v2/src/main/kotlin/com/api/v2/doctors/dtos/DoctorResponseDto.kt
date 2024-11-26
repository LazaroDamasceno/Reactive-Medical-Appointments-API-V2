package com.api.v2.doctors.dtos

import com.api.v2.people.dtos.PersonResponseDto

data class DoctorResponseDto(
    val licenseNumber: String,
    val personResponseDto: PersonResponseDto
)
