package com.api.v2.customers.services

import com.api.v2.customers.domain.Customer
import com.api.v2.customers.domain.CustomerRepository
import com.api.v2.customers.utils.CustomerFinderUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
internal class CustomerRetrievalServiceImpl(
    private val customerFinderUtil: CustomerFinderUtil,
    private val customerRepository: CustomerRepository
): CustomerRetrievalService {

    override suspend fun findBySsn(ssn: String): Customer {
        return withContext(Dispatchers.IO) {
            customerFinderUtil.find(ssn)
        }
    }

    override suspend fun findAll(): Flow<Customer> {
        return withContext(Dispatchers.IO) {
            customerRepository.findAll()
        }
    }

}