package com.api.v2.customers.services

import com.api.v2.customers.domain.Customer
import com.api.v2.customers.dtos.CustomerRegistrationDto

interface CustomerRegistrationService {
    suspend fun register(registrationDto: CustomerRegistrationDto): Customer
}