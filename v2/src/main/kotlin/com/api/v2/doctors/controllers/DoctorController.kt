package com.api.v2.doctors.controllers

import com.api.v2.doctors.dtos.DoctorRegistrationDto
import com.api.v2.doctors.dtos.DoctorResponseDto
import com.api.v2.doctors.services.DoctorRegistrationService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v2/doctors")
class DoctorController(
    private val registrationService: DoctorRegistrationService
) {

    @PostMapping
    suspend fun register(@RequestBody registrationDto: @Valid DoctorRegistrationDto): DoctorResponseDto {
        return registrationService.register(registrationDto)
    }

}