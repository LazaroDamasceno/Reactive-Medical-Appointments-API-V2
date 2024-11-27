package com.api.v2

import com.api.v2.people.domain.Person
import com.api.v2.people.domain.PersonRepository
import com.api.v2.people.dtos.PersonModificationDto
import com.api.v2.people.exceptions.UnregisteredSsnException
import com.api.v2.people.services.PersonModificationService
import com.api.v2.people.utils.PersonFinderUtil
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonModificationTest {

    @Autowired
    private lateinit var personFinderUtil: PersonFinderUtil

    @Autowired
    private lateinit var personRepository: PersonRepository

    @Autowired
    private lateinit var personModificationService: PersonModificationService

    val modificationDto = PersonModificationDto(
        "Leonardo",
        "Silva",
        "Santos Jr",
        LocalDate.parse("2000-12-12"),
        "jr@leosantos.com",
        "0987654321"
    )

    @Test
    fun `test successful modification`(): Unit = runBlocking {
        val foundPerson = personFinderUtil.findBySsn("123456789")
        val actual = personModificationService.modify(foundPerson, modificationDto)::class
        val expected = Person::class
        assertEquals(expected, actual)
    }

    @Test
    fun `test unsuccessful for non-existent SSN`(): Unit = runBlocking {
        assertThrows<UnregisteredSsnException> {
            val foundPerson = personFinderUtil.findBySsn("123456788")
            personModificationService.modify(foundPerson, modificationDto)
        }
    }

}