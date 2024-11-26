package com.api.v2.doctors.controllers

import com.api.v2.doctors.dtos.DoctorRegistrationDto
import com.api.v2.doctors.dtos.DoctorResponseDto
import com.api.v2.doctors.services.DoctorRegistrationService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/doctors")
class DoctorController(
    private val registrationService: DoctorRegistrationService
) {

    @PostMapping
    suspend fun register(@RequestBody registrationDto: @Valid DoctorRegistrationDto): DoctorResponseDto {
        return registrationService.register(registrationDto)
    }

}