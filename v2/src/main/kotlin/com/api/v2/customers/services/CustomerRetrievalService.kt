package com.api.v2.customers.services

import com.api.v2.customers.domain.Customer
import kotlinx.coroutines.flow.Flow

interface CustomerRetrievalService {
    suspend fun findBySsn(ssn: String): Customer
    suspend fun findAll(): Flow<Customer>
}