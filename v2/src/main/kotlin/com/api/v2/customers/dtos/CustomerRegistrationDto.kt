package com.api.v2.customers.dtos

import com.api.v2.people.dtos.PersonRegistrationDto

data class CustomerRegistrationDto(
    val personRegistrationDto: PersonRegistrationDto,
    val customerAddressDto: CustomerAddressDto
)
