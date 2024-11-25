package com.api.v2

import com.api.v2.customers.dtos.CustomerAddressDto
import com.api.v2.customers.dtos.CustomerModificationDto
import com.api.v2.people.dtos.PersonFullNameDto
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
class CustomerModificationTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    val customerModificationDto = CustomerModificationDto(
        PersonModificationDto(
            PersonFullNameDto(
                "Leo",
                "",
                "Santos"
            ),
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
    fun `test successful modification`() {
        val ssn = "123456789"
        webTestClient
            .put()
            .uri("api/v2/customers/$ssn")
            .bodyValue(customerModificationDto)
            .exchange()
            .expectStatus().is2xxSuccessful()
    }

    @Test
    @Order(2)
    fun `test unsuccessful for not found customer`() {
        val ssn = "123456788"
        webTestClient
            .put()
            .uri("api/v2/customers/$ssn")
            .bodyValue(customerModificationDto)
            .exchange()
            .expectStatus().is5xxServerError()
    }

}