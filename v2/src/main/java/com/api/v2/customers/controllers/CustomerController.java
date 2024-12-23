package com.api.v2.customers.controllers;

import com.api.v2.customers.dtos.CustomerModificationDto;
import com.api.v2.customers.dtos.CustomerRegistrationDto;
import com.api.v2.customers.dtos.CustomerResponseDto;
import com.api.v2.customers.services.CustomerModificationService;
import com.api.v2.customers.services.CustomerRegistrationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v2/customers")
public class CustomerController {

    private final CustomerRegistrationService registrationService;
    private final CustomerModificationService modificationService;

    public CustomerController(
            CustomerRegistrationService registrationService,
            CustomerModificationService modificationService
    ) {
        this.registrationService = registrationService;
        this.modificationService = modificationService;
    }

    @PostMapping
    public Mono<CustomerResponseDto> register(@Valid @RequestBody CustomerRegistrationDto registrationDto) {
        return registrationService.register(registrationDto);
    }

    @PatchMapping("{ssn}")
    public Mono<Void> modify(@PathVariable String ssn, @Valid @RequestBody CustomerModificationDto modificationDto) {
        return modificationService.modify(ssn, modificationDto);
    }
}
