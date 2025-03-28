package com.api.v2.medical_slots.services.impl;

import com.api.v2.doctors.domain.Doctor;
import com.api.v2.doctors.utils.DoctorFinder;
import com.api.v2.medical_appointments.domain.MedicalAppointment;
import com.api.v2.medical_appointments.domain.MedicalAppointmentRepository;
import com.api.v2.medical_slots.domain.MedicalSlot;
import com.api.v2.medical_slots.domain.MedicalSlotRepository;
import com.api.v2.medical_slots.exceptions.ImmutableMedicalSlotException;
import com.api.v2.medical_slots.exceptions.InaccessibleMedicalSlotException;
import com.api.v2.medical_slots.services.interfaces.MedicalSlotCancellationService;
import com.api.v2.medical_slots.utils.MedicalSlotFinder;

import java.util.Optional;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MedicalSlotCancellationServiceImpl implements MedicalSlotCancellationService {

    private final MedicalSlotFinder medicalSlotFinder;
    private final MedicalSlotRepository medicalSlotRepository;
    private final MedicalAppointmentRepository medicalAppointmentRepository;
    private final DoctorFinder doctorFinder;

    public MedicalSlotCancellationServiceImpl(
            MedicalSlotFinder medicalSlotFinder,
            MedicalSlotRepository medicalSlotRepository,
            MedicalAppointmentRepository medicalAppointmentRepository,
            DoctorFinder doctorFinder
    ) {
        this.medicalSlotFinder = medicalSlotFinder;
        this.medicalSlotRepository = medicalSlotRepository;
        this.medicalAppointmentRepository = medicalAppointmentRepository;
        this.doctorFinder = doctorFinder;
    }

    @Override
    public Mono<Void> cancel(String medicalLicenseNumber, String slotId) {
        return medicalSlotFinder
                .findById(slotId)
                .flatMap(slot -> {
                    return onCanceledMedicalSlot(slot)
                            .then(onCompletedMedicalSlot(slot))
                            .then(Mono.defer(() -> {
                                return doctorFinder
                                        .findByLicenseNumber(medicalLicenseNumber)
                                        .flatMap(doctor -> {
                                            return onNonAssociatedMedicalSlotWithDoctor(slot, doctor)
                                                    .then(Mono.defer(() -> {
                                                        Optional<MedicalAppointment> optional = Optional.ofNullable(slot.getMedicalAppointment());
                                                        if (optional.isPresent()) {
                                                            slot.markAsCanceled();
                                                            MedicalAppointment medicalAppointment = slot.getMedicalAppointment();
                                                            medicalAppointment.markAsCanceled();
                                                            slot.setMedicalAppointment(medicalAppointment);
                                                            return medicalSlotRepository
                                                                    .save(slot)
                                                                    .then(medicalAppointmentRepository.save(medicalAppointment));
                                                        }
                                                        slot.markAsCanceled();
                                                        return medicalSlotRepository.save(slot);
                                                    }));
                                        });
                            }));
                })
                .then();
    }

    private Mono<Void> onNonAssociatedMedicalSlotWithDoctor(MedicalSlot medicalSlot, Doctor doctor) {
        if (!medicalSlot.getDoctor().getId().equals(doctor.getId())) {
            return Mono.error(new InaccessibleMedicalSlotException(doctor.getId(), medicalSlot.getId()));
        }
        return Mono.empty();
    }

    private Mono<Void> onCanceledMedicalSlot(MedicalSlot slot) {
        String message = "Medical slot whose id is %s is already canceled.".formatted(slot.getId());
        if (slot.getCompletedAt() == null && slot.getCanceledAt() != null) {
            return Mono.error(new ImmutableMedicalSlotException(message));
        }
        return Mono.empty();
    }

    private Mono<Void> onCompletedMedicalSlot(MedicalSlot slot) {
        String message = "Medical slot whose id is %s is already completed.".formatted(slot.getId());
        if (slot.getCompletedAt() != null && slot.getCanceledAt() == null) {
            return Mono.error(new ImmutableMedicalSlotException(message));
        }
        return Mono.empty();
    }
}
