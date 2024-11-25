package com.api.v2.customers.services

import com.api.v2.customers.dtos.CustomerRegistrationDto
import com.api.v2.customers.dtos.CustomerResponseDto

interface CustomerRegistrationService {
    suspend fun register(registrationDto: CustomerRegistrationDto): CustomerResponseDto
}