package com.api.v2.people.domain

import com.api.v2.people.dtos.PersonFullNameDto

class PersonFullName {

    lateinit var firstName: String
    lateinit var middleName: String
    lateinit var lastName: String

    companion object {
        fun create(dto: PersonFullNameDto): PersonFullName {
            val personFullName = PersonFullName()
            personFullName.firstName = dto.firstName
            personFullName.middleName = dto.middleName
            personFullName.lastName = dto.lastName
            return personFullName
        }
    }

}