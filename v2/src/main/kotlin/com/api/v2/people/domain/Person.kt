package com.api.v2.people.domain

import com.api.v2.people.dtos.PersonFullNameDto
import com.api.v2.people.dtos.PersonRegistrationDto
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

@Document
class Person(
    var fullName: PersonFullNameDto,
    var ssn: String,
    var birthDate: LocalDate,
    var email: String,
    var phoneNumber: String
) {

    @BsonId
    var id: ObjectId = ObjectId()
    val createdAt: LocalDateTime = LocalDateTime.now()
    val createdAtZone: ZoneId = ZoneId.systemDefault()

    companion object {
        fun create(registrationDto: PersonRegistrationDto): Person {
            return Person(
                registrationDto.fullNameDto,
                registrationDto.ssn,
                registrationDto.birthDate,
                registrationDto.email,
                registrationDto.phoneNumber
            )
        }
    }

}