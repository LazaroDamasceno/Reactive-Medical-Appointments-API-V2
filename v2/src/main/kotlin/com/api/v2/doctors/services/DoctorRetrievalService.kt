package com.api.v2.doctors.services

import com.api.v2.doctors.dtos.DoctorResponseDto
import com.api.v2.doctors.dtos.MedicalLicenseNumberDto
import kotlinx.coroutines.flow.Flow

interface DoctorRetrievalService {
    suspend fun findByMedicalLicenseNumber(licenseNumberDto: MedicalLicenseNumberDto): DoctorResponseDto
    suspend fun findAll(): Flow<DoctorResponseDto>
}