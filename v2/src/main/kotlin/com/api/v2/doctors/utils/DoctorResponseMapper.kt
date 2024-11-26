package com.api.v2.doctors.utils

import com.api.v2.doctors.domain.Doctor
import com.api.v2.doctors.dtos.DoctorResponseDto
import com.api.v2.people.utils.PersonResponseMapper

class DoctorResponseMapper {
    companion object {
        fun map(doctor: Doctor): DoctorResponseDto {
            return DoctorResponseDto(
                doctor.medicalLicenseNumber,
                PersonResponseMapper.map(doctor.person)
            )
        }
    }
}