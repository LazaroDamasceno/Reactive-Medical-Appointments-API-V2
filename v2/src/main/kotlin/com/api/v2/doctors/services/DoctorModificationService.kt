package com.api.v2.doctors.services

import com.api.v2.people.dtos.PersonModificationDto

interface DoctorModificationService {
    suspend fun modify(medicalLicenseNumber: String, modificationDto: PersonModificationDto)
}