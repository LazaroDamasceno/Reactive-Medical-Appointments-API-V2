package com.api.v2

import com.api.v2.people.dtos.PersonFullNameDto
import com.api.v2.people.dtos.PersonRegistrationDto
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.LocalDate

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerRegistrationTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    val registrationDto1 = PersonRegistrationDto(
        PersonFullNameDto(
            "Leo",
            "",
            "Santos"
        ),
        "123456789",
        LocalDate.parse("2000-12-12"),
        "leosantos@mail.com",
        "1234567890"
    )

    @Test
    @Order(1)
    fun `test successful registration`() {
        webTestClient
            .post()
            .uri("api/v2/customers")
            .bodyValue(registrationDto1)
            .exchange()
            .expectStatus().is2xxSuccessful()
    }

    @Test
    @Order(2)
    fun `test unsuccessful for duplicated SSN`() {
        webTestClient
            .post()
            .uri("api/v2/customers")
            .bodyValue(registrationDto1)
            .exchange()
            .expectStatus().is5xxServerError()
    }

    val registrationDto2 = PersonRegistrationDto(
        PersonFullNameDto(
            "Leo",
            "",
            "Santos"
        ),
        "123456788",
        LocalDate.parse("2000-12-12"),
        "leosantos@mail.com",
        "1234567890"
    )

    @Test
    @Order(3)
    fun `test unsuccessful for duplicated email`() {
        webTestClient
            .post()
            .uri("api/v2/customers")
            .bodyValue(registrationDto2)
            .exchange()
            .expectStatus().is5xxServerError()
    }

}