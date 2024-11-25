package com.api.v2.customers.dtos

import com.api.v2.people.dtos.PersonResponseDto

data class CustomerResponseDto(
    val personResponseDto: PersonResponseDto,
    val addressDto: CustomerAddressDto
)