package com.api.v2.doctors.utils

import com.api.v2.doctors.domain.Doctor
import com.api.v2.doctors.domain.DoctorRepository
import com.api.v2.doctors.exceptions.DoctorNotFoundException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class DoctorFinderUtil(
    private val doctorRepository: DoctorRepository
) {

    suspend fun findByMedicalLicenseNumber(licenseNumberDto: String): Doctor {
        return withContext(Dispatchers.IO) {
            val foundDoctor = doctorRepository
                .findAll()
                .filter { d -> d.medicalLicenseNumber == licenseNumberDto }
                .singleOrNull()
            if (foundDoctor == null) throw DoctorNotFoundException()
            foundDoctor
        }
    }

}