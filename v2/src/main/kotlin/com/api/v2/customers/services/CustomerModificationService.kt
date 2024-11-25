package com.api.v2.customers.services

import com.api.v2.customers.dtos.CustomerModificationDto

interface CustomerModificationService {
    suspend fun modify(ssn: String, customerModificationDto: CustomerModificationDto)
}