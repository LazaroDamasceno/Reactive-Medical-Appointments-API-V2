package com.api.v2

import com.api.v2.people.dtos.PersonModificationDto
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
class DoctorModificationTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    val modificationDto = PersonModificationDto(
            "Ivan",
            "Silva",
            "Silveira Jr.",
            LocalDate.parse("2000-12-12"),
            "jr@ivansilveira.com",
            "1234567890"
    )

    @Test
    @Order(1)
    fun `test successful modification`() {
        val medicalLicenseNumber = "12345678CA"
        webTestClient
            .patch()
            .uri("api/v2/doctors/$medicalLicenseNumber")
            .bodyValue(modificationDto)
            .exchange()
            .expectStatus().is2xxSuccessful()
    }

    @Test
    @Order(2)
    fun `test unsuccessful for non-registered medical license number`() {
        val medicalLicenseNumber = "12345677CA"
        webTestClient
            .patch()
            .uri("api/v2/doctors/$medicalLicenseNumber")
            .bodyValue(modificationDto)
            .exchange()
            .expectStatus().is5xxServerError()
    }

}