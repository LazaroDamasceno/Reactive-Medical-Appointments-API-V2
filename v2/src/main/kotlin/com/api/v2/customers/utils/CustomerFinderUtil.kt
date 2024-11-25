package com.api.v2.customers.utils

import com.api.v2.customers.domain.Customer
import com.api.v2.customers.domain.CustomerRepository
import com.api.v2.people.utils.PersonFinderUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class CustomerFinderUtil(
    private val personFinderUtil: PersonFinderUtil,
    private val customerRepository: CustomerRepository
) {

    suspend fun find(ssn: String): Customer {
        return withContext(Dispatchers.IO) {
            val foundPerson = personFinderUtil.find(ssn)
            customerRepository
                .findAll()
                .filter { c -> c.person == foundPerson }
                .single()
        }
    }

}