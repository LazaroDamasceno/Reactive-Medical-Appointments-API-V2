package com.api.v2.people.domain

import com.api.v2.people.dtos.PersonModificationDto
import com.api.v2.people.dtos.PersonRegistrationDto
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

@Document
class Person(
    var firstName: String,
    var middleName: String,
    var lastName: String,
    var ssn: String,
    var birthDate: LocalDate,
    var email: String,
    var phoneNumber: String
) {

    @BsonId
    var id: ObjectId = ObjectId()
    val createdAt: LocalDateTime = LocalDateTime.now()
    val createdAtZone: ZoneId = ZoneId.systemDefault()
    var modifiedAt: LocalDateTime? = null
    var modifiedAtZone: ZoneId? = null

    companion object {
        fun create(registrationDto: PersonRegistrationDto): Person {
            return Person(
                registrationDto.firstName,
                registrationDto.middleName,
                registrationDto.lastName,
                registrationDto.ssn,
                registrationDto.birthDate,
                registrationDto.email,
                registrationDto.phoneNumber
            )
        }
    }

    fun modify(modificationDto: PersonModificationDto) {
        firstName = modificationDto.firstName
        middleName = modificationDto.middle
        lastName = modificationDto.lastName
        birthDate = modificationDto.birthDate
        email = modificationDto.email
        phoneNumber = modificationDto.phoneNumber
        modifiedAt = LocalDateTime.now()
        modifiedAtZone = ZoneId.systemDefault()
    }

    fun fullName(): String {
        if (middleName.isEmpty()) "$firstName $lastName"
        return "$firstName $middleName $lastName"
    }

}