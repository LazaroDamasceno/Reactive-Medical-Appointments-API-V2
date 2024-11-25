package com.api.v2.people.utils

import com.api.v2.people.domain.Person
import com.api.v2.people.domain.PersonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class PersonFinderUtil(
    private val repository: PersonRepository
) {

    suspend fun find(ssn: String): Person? {
        return withContext(Dispatchers.IO) {
            repository
                .findAll()
                .filter { p -> p.ssn == ssn }
                .singleOrNull()
        }
    }

}