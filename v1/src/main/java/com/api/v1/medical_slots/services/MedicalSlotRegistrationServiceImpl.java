package com.api.v1.medical_slots.services;

import com.api.v1.common.DuplicatedBookingDateTimeException;
import com.api.v1.doctors.domain.exposed.Doctor;
import com.api.v1.doctors.utils.DoctorFinder;
import com.api.v1.medical_slots.domain.exposed.MedicalSlot;
import com.api.v1.medical_slots.domain.MedicalSlotRepository;
import com.api.v1.medical_slots.response.MedicalSlotResponseDto;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalDateTime;

@Service
public class MedicalSlotRegistrationServiceImpl implements MedicalSlotRegistrationService {

    private final MedicalSlotRepository medicalSlotRepository;
    private final DoctorFinder doctorFinder;

    public MedicalSlotRegistrationServiceImpl(MedicalSlotRepository medicalSlotRepository,
                                              DoctorFinder doctorFinder
    ) {
        this.medicalSlotRepository = medicalSlotRepository;
        this.doctorFinder = doctorFinder;
    }

    @Override
    public Mono<ResponseEntity<MedicalSlotResponseDto>> register(String doctorId, @NotNull LocalDateTime availableAt) {
        return doctorFinder
                .findById(doctorId)
                .flatMap(foundDoctor -> {
                    return validate(foundDoctor, availableAt)
                            .then(Mono.defer(() -> {
                                MedicalSlot medicalSlot = MedicalSlot.of(foundDoctor, availableAt);
                                return medicalSlotRepository
                                        .save(medicalSlot)
                                        .map(MedicalSlot::toDto);
                            }));
                })
                .map(responseDto -> {
                    return ResponseEntity
                            .created(URI.create("/api/v1/medical-slots"))
                            .body(responseDto);
                });
    }

    private Mono<Object> validate(Doctor doctor,LocalDateTime availableAt) {
        String message = "Provided booking date and time is currently in use in another active medical slot.";
        return medicalSlotRepository
                .findActiveByDoctorAndAvailableAt(doctor.getId(), availableAt)
                .switchIfEmpty(Mono.empty())
                .flatMap(_ -> Mono.error(new DuplicatedBookingDateTimeException(message)));
    }
}
