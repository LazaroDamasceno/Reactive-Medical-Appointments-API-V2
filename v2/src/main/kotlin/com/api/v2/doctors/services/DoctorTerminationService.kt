package com.api.v2.doctors.services

interface DoctorTerminationService {
    suspend fun terminate(licenseNumber: String)
}