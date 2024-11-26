package com.api.v2.doctors.dtos

import com.api.v2.people.dtos.PersonResponseDto

data class DoctorResponseDto(
    val licenseNumberDto: MedicalLicenseNumberDto,
    val personResponseDto: PersonResponseDto
)
