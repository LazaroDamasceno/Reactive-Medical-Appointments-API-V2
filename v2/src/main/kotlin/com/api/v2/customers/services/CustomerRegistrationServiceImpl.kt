package com.api.v2.customers.services

import com.api.v2.customers.domain.Customer
import com.api.v2.customers.dtos.CustomerRegistrationDto
import com.api.v2.firestore.FirestoreCollections
import com.api.v2.people.services.PersonRegistrationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
class CustomerRegistrationServiceImpl(
    private val personRegistrationService: PersonRegistrationService
): CustomerRegistrationService {

    override suspend fun register(registrationDto: CustomerRegistrationDto) {
        return withContext(Dispatchers.IO) {
            val savedPerson = personRegistrationService.register(registrationDto.personRegistrationDto)
            val customer = Customer.create(savedPerson, registrationDto.customerAddressDto)
            val savedCustomer = FirestoreCollections
                .getCustomersCollectionReference()
                .add(customer)
            val customerId = savedCustomer.get().id
            savedCustomer.get().update("id", customerId)
            savedCustomer.get().get().get().toObject(Customer::class.java)
        }
    }

}