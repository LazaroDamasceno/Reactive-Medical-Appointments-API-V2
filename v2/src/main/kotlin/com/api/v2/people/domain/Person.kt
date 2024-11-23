package com.api.v2.people.domain

import com.api.v2.people.dtos.PersonRegistrationDto
import java.time.LocalDateTime
import java.time.ZoneId

class Person {

    var id: String = ""
    lateinit var fullName: PersonFullName
    var ssn: String = ""
    var birthDate: String = ""
    var email: String = ""
    var phoneNumber: String = ""
    val createdAt: String = LocalDateTime.now().toString()
    val createdAtZone: String = ZoneId.systemDefault().toString()

    companion object {
        fun create(registrationDto: PersonRegistrationDto): Person {
            val person = Person()
            person.fullName = PersonFullName.create(registrationDto.fullNameDto)
            person.ssn = registrationDto.ssn
            person.birthDate = registrationDto.birthDate.toString()
            person.email = registrationDto.email
            person.phoneNumber = registrationDto.phoneNumber
            return person
        }
    }

}