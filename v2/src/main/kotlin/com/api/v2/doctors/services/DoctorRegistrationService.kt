package com.api.v2.doctors.services

import com.api.v2.doctors.dtos.DoctorRegistrationDto
import com.api.v2.doctors.dtos.DoctorResponseDto

interface DoctorRegistrationService {
    suspend fun register(registrationDto: DoctorRegistrationDto): DoctorResponseDto
}