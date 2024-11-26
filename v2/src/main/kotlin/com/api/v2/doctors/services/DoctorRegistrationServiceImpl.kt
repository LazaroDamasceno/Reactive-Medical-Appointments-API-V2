package com.api.v2.doctors.services

import com.api.v2.doctors.domain.Doctor
import com.api.v2.doctors.domain.DoctorRepository
import com.api.v2.doctors.dtos.DoctorRegistrationDto
import com.api.v2.doctors.dtos.DoctorResponseDto
import com.api.v2.doctors.exceptions.DuplicatedMedicalLicenseNumberException
import com.api.v2.doctors.utils.DoctorResponseMapper
import com.api.v2.people.services.PersonRegistrationService
import jakarta.validation.Valid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
internal class DoctorRegistrationServiceImpl(
    private val doctorRepository: DoctorRepository,
    private val personRegistrationService: PersonRegistrationService
): DoctorRegistrationService {

    override suspend fun register(registrationDto: @Valid DoctorRegistrationDto): DoctorResponseDto {

        suspend fun handleDuplicatedMedicalLicenseNumber(medicalLicenseNumber: String) {
            val isMedicalLicenseNumberDuplicated = doctorRepository
                .findAll()
                .filter { ln -> ln.medicalLicenseNumber == medicalLicenseNumber }
                .singleOrNull() != null
            if (isMedicalLicenseNumberDuplicated) throw DuplicatedMedicalLicenseNumberException()
        }

        return withContext(Dispatchers.IO) {
            handleDuplicatedMedicalLicenseNumber(registrationDto.medicalLicenseNumber)
            val savedPerson = personRegistrationService.register(registrationDto.personRegistrationDto)
            val doctor = Doctor.create(registrationDto.medicalLicenseNumber, savedPerson)
            val savedDoctor = doctorRepository.save(doctor)
            DoctorResponseMapper.map(savedDoctor)
        }
    }

}