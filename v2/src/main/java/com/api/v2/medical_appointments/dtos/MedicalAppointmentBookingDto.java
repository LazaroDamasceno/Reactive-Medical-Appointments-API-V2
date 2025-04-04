package com.api.v2.medical_appointments.dtos;

import java.time.LocalDateTime;

public record MedicalAppointmentBookingDto(
        String customerId,
        String medicalLicenseNumber,
        LocalDateTime bookedAt
) {
}
