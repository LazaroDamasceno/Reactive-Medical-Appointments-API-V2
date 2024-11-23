package com.api.v2.customers.services

import com.api.v2.customers.domain.Customer
import com.api.v2.customers.domain.CustomerRepository
import com.api.v2.customers.dtos.CustomerRegistrationDto
import com.api.v2.people.services.PersonRegistrationService
import jakarta.validation.Valid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
internal class CustomerRegistrationServiceImpl(
    private val personRegistrationService: PersonRegistrationService,
    private val customerRepository: CustomerRepository
): CustomerRegistrationService {

    override suspend fun register(registrationDto: @Valid CustomerRegistrationDto): Customer {
        return withContext(Dispatchers.IO) {
            val savedPerson = personRegistrationService.register(registrationDto.personRegistrationDto)
            val customer = Customer.create(savedPerson, registrationDto.customerAddressDto)
            customerRepository.save(customer)
        }
    }

}