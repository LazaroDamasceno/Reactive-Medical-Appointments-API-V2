package com.api.v2

import com.api.v2.people.domain.Person
import com.api.v2.people.dtos.PersonRegistrationDto
import com.api.v2.people.exceptions.DuplicatedPersonalInformationException
import com.api.v2.people.services.PersonRegistrationService
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate
import kotlin.test.assertEquals

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonRegistrationTest {

	@Autowired
	private lateinit var registrationService: PersonRegistrationService

	val registrationDto1 =  PersonRegistrationDto(
		"Leo",
		"",
		"Santos",
		"123456789",
		LocalDate.parse("2000-12-12"),
		"leosantos@mail.com",
		"1234567890",
	)

	@Test
	@Order(1)
	fun `test successful registration`(): Unit = runBlocking {
		val actualResult = registrationService.register(registrationDto1)::class
		val expectedResult = Person::class
		assertEquals(expectedResult, actualResult)
	}

	@Test
	@Order(2)
	fun `test unsuccessful for duplicated SSN`(): Unit = runBlocking {
		assertThrows<DuplicatedPersonalInformationException> {
			registrationService.register(registrationDto1)
		}
	}

	val registrationDto2 =  PersonRegistrationDto(
		"Leo",
		"",
		"Santos",
		"123456789",
		LocalDate.parse("2000-12-12"),
		"leosantos@mail.com",
		"1234567890",
	)

	@Test
	@Order(3)
	fun `test unsuccessful for duplicated email`(): Unit = runBlocking {
		assertThrows<DuplicatedPersonalInformationException> {
			registrationService.register(registrationDto2)
		}
	}

}
