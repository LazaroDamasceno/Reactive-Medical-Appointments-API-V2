package com.api.v2.medical_slots.services.interfaces;

import com.api.v2.medical_slots.domain.MedicalSlot;
import reactor.core.publisher.Mono;

public interface MedicalSlotCompletionService {
    Mono<Void> complete(MedicalSlot medicalSlot);
}