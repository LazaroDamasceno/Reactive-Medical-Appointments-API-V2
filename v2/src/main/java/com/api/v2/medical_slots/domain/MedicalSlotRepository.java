package com.api.v2.medical_slots.domain;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MedicalSlotRepository extends ReactiveMongoRepository<MedicalSlot, String> {
}
