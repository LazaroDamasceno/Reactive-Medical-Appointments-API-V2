package com.api.v2.customers.controllers

import com.api.v2.customers.domain.Customer
import com.api.v2.customers.dtos.CustomerRegistrationDto
import com.api.v2.customers.services.CustomerRegistrationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v2/customers")
class CustomerController(
    private val registrationService: CustomerRegistrationService
) {

    @PostMapping
    suspend fun register(registrationDto: CustomerRegistrationDto): Customer {
        return registrationService.register(registrationDto)
    }

}