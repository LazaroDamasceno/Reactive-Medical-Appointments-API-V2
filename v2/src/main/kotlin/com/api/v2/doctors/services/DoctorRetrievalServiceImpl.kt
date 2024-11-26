package com.api.v2.doctors.services

import com.api.v2.doctors.domain.DoctorRepository
import com.api.v2.doctors.dtos.DoctorResponseDto
import com.api.v2.doctors.dtos.MedicalLicenseNumberDto
import com.api.v2.doctors.utils.DoctorFinderUtil
import com.api.v2.doctors.utils.DoctorResponseMapper
import jakarta.validation.Valid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
internal class DoctorRetrievalServiceImpl(
    private val doctorFinderUtil: DoctorFinderUtil,
    private val doctorRepository: DoctorRepository
): DoctorRetrievalService {

    override suspend fun findByMedicalLicenseNumber(
        licenseNumberDto: @Valid MedicalLicenseNumberDto
    ): DoctorResponseDto {
        return withContext(Dispatchers.IO) {
            val foundDoctor = doctorFinderUtil.findByMedicalLicenseNumber(licenseNumberDto)
            DoctorResponseMapper.map(foundDoctor)
        }
    }

    override suspend fun findAll(): Flow<DoctorResponseDto> {
        return withContext(Dispatchers.IO) {
            doctorRepository.findAll().map { d -> DoctorResponseMapper.map(d) }
        }
    }

}