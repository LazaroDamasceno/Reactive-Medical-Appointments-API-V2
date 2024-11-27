package com.api.v2.doctors.services

import com.api.v2.doctors.domain.DoctorRepository
import com.api.v2.doctors.utils.DoctorFinderUtil
import com.api.v2.people.dtos.PersonModificationDto
import com.api.v2.people.services.PersonModificationService
import jakarta.validation.Valid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
internal class DoctorModificationServiceImpl(
    private val doctorFinderUtil: DoctorFinderUtil,
    private val personModificationService: PersonModificationService,
    private val doctorRepository: DoctorRepository
): DoctorModificationService {

    override suspend fun modify(medicalLicenseNumber: String, modificationDto: @Valid PersonModificationDto) {
        return withContext(Dispatchers.IO) {
            val foundDoctor = doctorFinderUtil.findByMedicalLicenseNumber(medicalLicenseNumber)
            val modifiedPerson = personModificationService.modify(foundDoctor.person, modificationDto)
            foundDoctor.person = modifiedPerson
            doctorRepository.save(foundDoctor)
        }
    }

}