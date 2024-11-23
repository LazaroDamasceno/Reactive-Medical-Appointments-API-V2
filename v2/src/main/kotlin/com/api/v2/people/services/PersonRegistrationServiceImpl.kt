package com.api.v2.people.services

import com.api.v2.firestore.FirestoreCollections
import com.api.v2.people.domain.Person
import com.api.v2.people.dtos.PersonRegistrationDto
import com.api.v2.people.exceptions.DuplicatedPersonalInformationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
class PersonRegistrationServiceImpl: PersonRegistrationService {

    override suspend fun register(registrationDto: PersonRegistrationDto) {
        return withContext(Dispatchers.IO) {
            val isSsnDuplicated = !FirestoreCollections
                .getPeopleCollectionReference()
                .whereEqualTo("ssn", registrationDto.ssn)
                .get()
                .get()
                .isEmpty
            var message = "The given SSN is already in use."
            if (isSsnDuplicated) throw DuplicatedPersonalInformationException(message)
            val isEmailDuplicated = !FirestoreCollections
                .getPeopleCollectionReference()
                .whereEqualTo("email", registrationDto.email)
                .get()
                .get()
                .isEmpty
            message = "The given email is already in use."
            if (isEmailDuplicated) throw DuplicatedPersonalInformationException(message)
            val person = Person.create(registrationDto)
            val savedPerson = FirestoreCollections
                .getPeopleCollectionReference()
                .add(person)
            val personID = savedPerson.get().id
            savedPerson.get().update("id", personID)
            savedPerson.get().get().get().toObject(Person::class.java)
        }
    }

}