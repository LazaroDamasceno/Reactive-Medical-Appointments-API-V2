package com.api.v2.customers.dtos

import com.api.v2.people.dtos.PersonRegistrationDto
import jakarta.validation.Valid

data class CustomerRegistrationDto(
    val personRegistrationDto: @Valid PersonRegistrationDto,
    val customerAddressDto: @Valid CustomerAddressDto
)
