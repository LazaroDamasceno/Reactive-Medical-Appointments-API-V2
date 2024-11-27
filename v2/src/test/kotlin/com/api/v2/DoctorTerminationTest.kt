package com.api.v2

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DoctorTerminationTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    @Order(1)
    fun `test successful termination`() {
        val licenseNumber = "12345678CA"
        webTestClient
            .patch()
            .uri("api/v2/doctors/$licenseNumber")
            .exchange()
            .expectStatus().is2xxSuccessful()
    }

    @Test
    @Order(2)
    fun `test unsuccessful for already terminated doctor`() {
        val licenseNumber = "12345678CA"
        webTestClient
            .patch()
            .uri("api/v2/doctors/$licenseNumber")
            .exchange()
            .expectStatus().is5xxServerError()
    }

    @Test
    @Order(3)
    fun `test unsuccessful for non-existent license number`() {
        val licenseNumber = "12345677CA"
        webTestClient
            .patch()
            .uri("api/v2/doctors/$licenseNumber")
            .exchange()
            .expectStatus().is5xxServerError()
    }

}