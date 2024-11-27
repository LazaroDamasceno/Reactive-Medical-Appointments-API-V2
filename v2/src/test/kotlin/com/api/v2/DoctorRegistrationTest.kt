package com.api.v2

import com.api.v2.doctors.dtos.DoctorRegistrationDto
import com.api.v2.people.dtos.PersonRegistrationDto
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.LocalDate

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DoctorRegistrationTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    val registrationDto1 = DoctorRegistrationDto(
        "12345678CA",
        PersonRegistrationDto(
            "Ivan",
            "",
            "Silveira",
            "987654321",
            LocalDate.parse("2000-12-12"),
            "ivansilveira@mail.com",
            "1234567890",
        )
    )

    @Test
    @Order(1)
    fun `test successful registration`() {
        webTestClient
            .post()
            .uri("api/v2/doctors")
            .bodyValue(registrationDto1)
            .exchange()
            .expectStatus().is2xxSuccessful()
    }

    @Test
    @Order(2)
    fun `test unsuccessful for duplicated medical license number`() {
        webTestClient
            .post()
            .uri("api/v2/doctors")
            .bodyValue(registrationDto1)
            .exchange()
            .expectStatus().is5xxServerError()
    }

    val registrationDto2 = DoctorRegistrationDto(
        "12345677CA",
        PersonRegistrationDto(
            "Ivan",
            "",
            "Silveira",
            "987654321",
            LocalDate.parse("2000-12-12"),
            "ivansilveira@mail.com",
            "1234567890",
        )
    )

    @Test
    @Order(3)
    fun `test unsuccessful for duplicated SSN`() {
        webTestClient
            .post()
            .uri("api/v2/doctors")
            .bodyValue(registrationDto2)
            .exchange()
            .expectStatus().is5xxServerError()
    }

    val registrationDto3 = DoctorRegistrationDto(
        "12345676CA",
        PersonRegistrationDto(
            "Ivan",
            "",
            "Silveira",
            "987654320",
            LocalDate.parse("2000-12-12"),
            "ivansilveira@mail.com",
            "1234567890",
        )
    )

    @Test
    @Order(4)
    fun `test unsuccessful for duplicated email`() {
        webTestClient
            .post()
            .uri("api/v2/doctors")
            .bodyValue(registrationDto3)
            .exchange()
            .expectStatus().is5xxServerError()
    }

}