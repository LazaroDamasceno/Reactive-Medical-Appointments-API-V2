package com.api.v2.customers.domain

import com.api.v2.customers.dtos.CustomerAddressDto

class CustomerAddress {

    lateinit var state: String
    lateinit var city: String
    lateinit var street: String
    lateinit var zipcode: String

    companion object {
        fun create(customerAddressDto: CustomerAddressDto): CustomerAddress {
            val customerAddress = CustomerAddress()
            customerAddress.state = customerAddressDto.state
            customerAddress.city = customerAddressDto.city
            customerAddress.street = customerAddressDto.street
            customerAddress.zipcode = customerAddressDto.zipcode
            return customerAddress
        }

    }

}