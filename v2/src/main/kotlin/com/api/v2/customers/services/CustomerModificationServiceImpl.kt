package com.api.v2.customers.services

import com.api.v2.customers.domain.CustomerRepository
import com.api.v2.customers.dtos.CustomerModificationDto
import com.api.v2.customers.utils.CustomerFinderUtil
import com.api.v2.people.services.PersonModificationService
import jakarta.validation.Valid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
internal class CustomerModificationServiceImpl(
    private val customerFinderUtil: CustomerFinderUtil,
    private val personModificationService: PersonModificationService,
    private val customerRepository: CustomerRepository
): CustomerModificationService {

    override suspend fun modify(ssn: String, customerModificationDto: @Valid CustomerModificationDto) {
        return withContext(Dispatchers.IO) {
            val foundCustomer = customerFinderUtil.find(ssn)
            val foundPerson = foundCustomer.person
            val modifiedPerson = personModificationService
                .modify(foundPerson, customerModificationDto.personModificationDto)
            foundCustomer.modify(modifiedPerson, customerModificationDto.customerAddressDto)
            customerRepository.save(foundCustomer)
        }
    }

}