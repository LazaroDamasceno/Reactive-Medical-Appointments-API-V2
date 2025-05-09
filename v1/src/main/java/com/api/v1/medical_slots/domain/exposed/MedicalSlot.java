package com.api.v1.medical_slots.domain.exposed;

import com.api.v1.doctors.domain.exposed.Doctor;
import com.api.v1.medical_appointments.domain.exposed.MedicalAppointment;
import com.api.v1.medical_slots.enums.MedicalSlotStatus;
import com.api.v1.medical_slots.response.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document
public class MedicalSlot {

    @Id
    private String id;
    private Doctor doctor;
    private LocalDateTime availableAt;
    private LocalDateTime createdAt;
    private MedicalSlotStatus status;
    private LocalDateTime canceledAt;
    private LocalDateTime completedAt;
    private MedicalAppointment medicalAppointment;

    private MedicalSlot() {
    }

    private MedicalSlot(Doctor doctor, LocalDateTime availableAt) {
        this.id = UUID.randomUUID().toString();
        this.doctor = doctor;
        this.availableAt = availableAt;
        this.createdAt = LocalDateTime.now();
        this.status = MedicalSlotStatus.ACTIVE;
    }

    public static MedicalSlot of(Doctor doctor, LocalDateTime availableAt) {
        return new MedicalSlot(doctor, availableAt);
    }

    public void markAsCanceled() {
        status = MedicalSlotStatus.CANCELED;
        canceledAt = LocalDateTime.now();
    }

    public void markAsCompleted() {
        status = MedicalSlotStatus.COMPLETED;
        completedAt = LocalDateTime.now();
    }

    public MedicalSlotResponseDto toDto() {
        if (status.equals(MedicalSlotStatus.CANCELED)) {
            return CanceledMedicalSlotResponseDto.from(this);
        }
        else if (status.equals(MedicalSlotStatus.COMPLETED)) {
            return CompletedMedicalSlotResponseDto.from(this);
        }
        return MedicalSlotResponseDto.from(this);
    }

    public String getId() {
        return id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public LocalDateTime getAvailableAt() {
        return availableAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public MedicalSlotStatus getStatus() {
        return status;
    }

    public LocalDateTime getCanceledAt() {
        return canceledAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public MedicalAppointment getMedicalAppointment() {
        return medicalAppointment;
    }

    public void setMedicalAppointment(MedicalAppointment medicalAppointment) {
        this.medicalAppointment = medicalAppointment;
    }
}
