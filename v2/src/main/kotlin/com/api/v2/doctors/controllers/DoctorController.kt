package com.api.v2.doctors.controllers

import com.api.v2.doctors.dtos.DoctorRegistrationDto
import com.api.v2.doctors.dtos.DoctorResponseDto
import com.api.v2.doctors.services.DoctorModificationService
import com.api.v2.doctors.services.DoctorRegistrationService
import com.api.v2.doctors.services.DoctorRetrievalService
import com.api.v2.doctors.services.DoctorTerminationService
import com.api.v2.people.dtos.PersonModificationDto
import jakarta.validation.Valid
import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v2/doctors")
class DoctorController(
    private val registrationService: DoctorRegistrationService,
    private val retrievalService: DoctorRetrievalService,
    private val modificationService: DoctorModificationService,
    private val terminationService: DoctorTerminationService
) {

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    suspend fun register(@RequestBody registrationDto: @Valid DoctorRegistrationDto): DoctorResponseDto {
        return registrationService.register(registrationDto)
    }

    @GetMapping("{licenseNumber}")
    @ResponseStatus(value = HttpStatus.OK)
    suspend fun findByMedicalLicenseNumber(@PathVariable licenseNumber: String): DoctorResponseDto {
        return retrievalService.findByMedicalLicenseNumber(licenseNumber)
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    suspend fun findAll(): Flow<DoctorResponseDto> {
        return retrievalService.findAll()
    }

    @PatchMapping("{licenseNumber}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    suspend fun modify(
        @PathVariable licenseNumber: String,
        @RequestBody modificationDto: @Valid PersonModificationDto
    ) {
        return modificationService.modify(licenseNumber, modificationDto)
    }

    @PatchMapping("{licenseNumber}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    suspend fun terminate(@PathVariable licenseNumber: String) {
        return terminationService.terminate(licenseNumber)
    }

}