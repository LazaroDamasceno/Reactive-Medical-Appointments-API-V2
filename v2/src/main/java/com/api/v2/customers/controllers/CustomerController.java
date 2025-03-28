package com.api.v2.customers.controllers;

import com.api.v2.customers.dtos.CustomerRegistrationDto;
import com.api.v2.customers.dtos.CustomerResponseDto;
import com.api.v2.customers.services.CustomerRegistrationService;
import com.api.v2.customers.services.CustomerRetrievalService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v2/customers")
public class CustomerController {

    private final CustomerRegistrationService registrationService;
    private final CustomerRetrievalService retrievalService;

    public CustomerController(
            CustomerRegistrationService registrationService,
            CustomerRetrievalService retrievalService
    ) {
        this.registrationService = registrationService;
        this.retrievalService = retrievalService;
    }

    @Operation(summary = "Register a customer")
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<CustomerResponseDto> register(@Valid @RequestBody CustomerRegistrationDto registrationDto) {
        return registrationService.register(registrationDto);
    }

    @GetMapping("{id}")
    @Operation(summary = "Retrieve a customer by its id")
    public Mono<CustomerResponseDto> findById(@PathVariable String id) {
        return retrievalService.findById(id);
    }

    @Operation(summary = "Retrieve all customers")
    @GetMapping
    public Flux<CustomerResponseDto> findAll() {
        return retrievalService.findAll();
    }
}
