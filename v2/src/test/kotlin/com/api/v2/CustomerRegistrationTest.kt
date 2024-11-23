package com.api.v2

import com.api.v2.customers.domain.Customer
import com.api.v2.customers.dtos.CustomerAddressDto
import com.api.v2.customers.dtos.CustomerRegistrationDto
import com.api.v2.customers.services.CustomerRegistrationService
import com.api.v2.people.dtos.PersonFullNameDto
import com.api.v2.people.dtos.PersonRegistrationDto
import com.api.v2.people.exceptions.DuplicatedPersonalInformationException
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate
import kotlin.test.assertEquals

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerRegistrationTest {

    @Autowired
    private lateinit var registrationService: CustomerRegistrationService

    val registrationDto1 = CustomerRegistrationDto(
        PersonRegistrationDto(
            PersonFullNameDto(
             "Leo",
             "",
             "Santos"
            ),
            "123456789",
            LocalDate.parse("2000-12-12"),
            "leosantos@mail.com",
            "1234567890"
        ),
        CustomerAddressDto(
            "CA",
            "LA",
            "Downtown",
            "90012"
        )
    )

    @Test
    @Order(1)
    fun `test successful registration`(): Unit = runBlocking {
        val actualResult = registrationService.register(registrationDto1)::class
        val expectedResult = Customer::class
        assertEquals(expectedResult, actualResult)
    }

    @Test
    @Order(2)
    fun `test unsuccessful for duplicated SSN`(): Unit = runBlocking {
        assertThrows<DuplicatedPersonalInformationException> {
            registrationService.register(registrationDto1)
        }
    }

    val registrationDto2 = CustomerRegistrationDto(
        PersonRegistrationDto(
            PersonFullNameDto(
                "Leo",
                "",
                "Santos"
            ),
            "123456789",
            LocalDate.parse("2000-12-12"),
            "leosantos@mail.com",
            "1234567890"
        ),
        CustomerAddressDto(
            "CA",
            "LA",
            "Downtown",
            "90012"
        )
    )

    @Test
    @Order(3)
    fun `test unsuccessful for duplicated email`(): Unit = runBlocking {
        assertThrows<DuplicatedPersonalInformationException> {
            registrationService.register(registrationDto1)
        }
    }

}