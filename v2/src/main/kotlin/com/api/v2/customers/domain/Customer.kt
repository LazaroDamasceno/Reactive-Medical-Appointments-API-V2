package com.api.v2.customers.domain

import com.api.v2.customers.dtos.CustomerAddressDto
import com.api.v2.people.domain.Person
import java.time.LocalDateTime
import java.time.ZoneId

class Customer {

    var id: String = ""
    lateinit var person: Person
    lateinit var address: CustomerAddressDto
    val createdAt: String = LocalDateTime.now().toString()
    val createdAtZone: String = ZoneId.systemDefault().toString()

    companion object {
        fun create(person: Person, addressDto: CustomerAddressDto): Customer {
            val customer = Customer()
            customer.person = person
            customer.address = addressDto
            return customer
        }
    }

}