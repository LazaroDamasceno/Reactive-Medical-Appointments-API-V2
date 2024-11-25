package com.api.v2.people.utils

import com.api.v2.people.domain.Person
import com.api.v2.people.domain.PersonRepository
import com.api.v2.people.exceptions.UnregisteredSsnException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class PersonFinderUtil(
    private val personRepository: PersonRepository
) {

    suspend fun findBySsn(ssn: String): Person {
        return withContext(Dispatchers.IO) {
            val foundPerson = personRepository
                .findAll()
                .filter { p -> p.ssn == ssn }
                .singleOrNull()
            if (foundPerson == null) throw UnregisteredSsnException()
            foundPerson
        }
    }

}