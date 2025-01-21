package com.api.v2.medical_slots.dtos;

import com.api.v2.medical_appointments.dtos.MedicalAppointmentResponseDto;
import com.api.v2.medical_appointments.utils.MedicalAppointmentResponseMapper;
import com.api.v2.medical_slots.domain.MedicalSlot;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class CompletedMedicalSlotResponseDto extends MedicalSlotResponseDto {

    private MedicalAppointmentResponseDto medicalAppointmentResponseDto;
    private LocalDateTime completedAt;
    private ZoneId completedAtZone;

    public CompletedMedicalSlotResponseDto() {
    }

    private CompletedMedicalSlotResponseDto(MedicalSlot medicalSlot) {
        super(medicalSlot);
        if (medicalSlot.getMedicalAppointment() == null) {
            return;
        }
        this.medicalAppointmentResponseDto = MedicalAppointmentResponseMapper.mapToDto(medicalSlot.getMedicalAppointment());
        this.completedAt = medicalSlot.getCanceledAt();
        this.completedAtZone = medicalSlot.getCanceledAtZone();
    }

    public static CompletedMedicalSlotResponseDto from(MedicalSlot medicalSlot) {
        return new CompletedMedicalSlotResponseDto(medicalSlot);
    }

    public MedicalAppointmentResponseDto getMedicalAppointmentResponseDto() {
        return medicalAppointmentResponseDto;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public ZoneId getCompletedAtZone() {
        return completedAtZone;
    }
}
