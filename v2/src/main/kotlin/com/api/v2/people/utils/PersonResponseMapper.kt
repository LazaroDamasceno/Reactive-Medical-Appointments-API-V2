package com.api.v2.people.utils

import com.api.v2.people.domain.Person
import com.api.v2.people.dtos.PersonResponseDto

class PersonResponseMapper {
    companion object {
        fun map(person: Person): PersonResponseDto {
            return PersonResponseDto(
                person.fullName,
                person.ssn,
                person.birthDate,
                person.email,
                person.phoneNumber
            )
        }
    }
}