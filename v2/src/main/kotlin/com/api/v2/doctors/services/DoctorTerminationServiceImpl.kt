package com.api.v2.doctors.services

import com.api.v2.doctors.domain.DoctorRepository
import com.api.v2.doctors.exceptions.TerminatedDoctorException
import com.api.v2.doctors.utils.DoctorFinderUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
internal class DoctorTerminationServiceImpl(
    private val doctorFinderUtil: DoctorFinderUtil,
    private val doctorRepository: DoctorRepository
): DoctorTerminationService {

    override suspend fun terminate(licenseNumber: String) {
        return withContext(Dispatchers.IO) {
            val foundDoctor = doctorFinderUtil.findByMedicalLicenseNumber(licenseNumber)
            if (foundDoctor.terminatedAt != null) throw TerminatedDoctorException()
            foundDoctor.terminate()
            doctorRepository.save(foundDoctor)
        }
    }

}