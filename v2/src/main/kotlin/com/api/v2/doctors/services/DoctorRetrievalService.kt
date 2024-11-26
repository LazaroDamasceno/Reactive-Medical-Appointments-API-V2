package com.api.v2.doctors.services

import com.api.v2.doctors.dtos.DoctorResponseDto
import kotlinx.coroutines.flow.Flow

interface DoctorRetrievalService {
    suspend fun findByMedicalLicenseNumber(licenseNumberDto: String): DoctorResponseDto
    suspend fun findAll(): Flow<DoctorResponseDto>
}