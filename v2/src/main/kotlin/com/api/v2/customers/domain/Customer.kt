package com.api.v2.customers.domain

import com.api.v2.people.domain.Person
import java.time.LocalDateTime
import java.time.ZoneId

class Customer {

    var id: String? = null
    lateinit var person: Person
    lateinit var address: CustomerAddress
    val createdAt: String = LocalDateTime.now().toString()
    val createdAtZone: String = ZoneId.systemDefault().toString()

    companion object {
        fun create(person: Person, address: CustomerAddress): Customer {
            val customer = Customer()
            customer.person = person
            customer.address = address
            return customer
        }
    }

}