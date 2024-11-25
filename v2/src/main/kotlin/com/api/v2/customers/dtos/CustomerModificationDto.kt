package com.api.v2.customers.dtos

import com.api.v2.people.dtos.PersonModificationDto
import jakarta.validation.Valid

data class CustomerModificationDto(
    val personModificationDto: @Valid PersonModificationDto,
    val customerAddressDto: @Valid CustomerAddressDto
)
