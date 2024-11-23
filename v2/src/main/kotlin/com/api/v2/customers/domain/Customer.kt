package com.api.v2.customers.domain

import com.api.v2.customers.dtos.CustomerAddressDto
import com.api.v2.people.domain.Person
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.time.ZoneId

@Document
class Customer(
    var person: Person,
    var address: CustomerAddressDto
) {

    @BsonId
    var id: ObjectId = ObjectId()
    val createdAt: String = LocalDateTime.now().toString()
    val createdAtZone: String = ZoneId.systemDefault().toString()

    companion object {
        fun create(person: Person, addressDto: CustomerAddressDto): Customer {
            return Customer(person, addressDto)
        }
    }

}