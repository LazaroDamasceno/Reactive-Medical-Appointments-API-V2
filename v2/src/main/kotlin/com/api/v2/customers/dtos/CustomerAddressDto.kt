package com.api.v2.customers.dtos

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CustomerAddressDto(
    val state: @NotBlank @Size(min=2, max=2) String,
    val city: @NotBlank String,
    val street: @NotBlank String,
    val zipcode: @NotBlank @Size(min=5, max=5) String
)
