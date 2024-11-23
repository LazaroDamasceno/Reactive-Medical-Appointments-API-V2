package com.api.v2.customers.dtos

data class CustomerAddressDto(
    val state: String,
    val city: String,
    val street: String,
    val zipcode: String
)
