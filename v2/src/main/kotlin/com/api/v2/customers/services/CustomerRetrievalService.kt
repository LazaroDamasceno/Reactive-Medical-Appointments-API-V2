package com.api.v2.customers.services

import com.api.v2.customers.dtos.CustomerResponseDto
import kotlinx.coroutines.flow.Flow

interface CustomerRetrievalService {
    suspend fun findBySsn(ssn: String): CustomerResponseDto
    suspend fun findAll(): Flow<CustomerResponseDto>
}