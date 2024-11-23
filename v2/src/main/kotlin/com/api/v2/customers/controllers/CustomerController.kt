package com.api.v2.customers.controllers

import com.api.v2.customers.domain.Customer
import com.api.v2.customers.dtos.CustomerRegistrationDto
import com.api.v2.customers.services.CustomerRegistrationService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RequestMapping("api/v2/customers")
class CustomerController(
    private val registrationService: CustomerRegistrationService
) {

    @PostMapping
    suspend fun register(@RequestBody registrationDto: @Valid CustomerRegistrationDto): Customer {
        return registrationService.register(registrationDto)
    }

}