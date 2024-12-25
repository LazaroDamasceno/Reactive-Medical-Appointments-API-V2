package com.api.v2.medical_appointments.exceptions;

public class NonExistentMedicalAppointmentException extends RuntimeException {
    public NonExistentMedicalAppointmentException(String id) {
        super("Medical appointment whose id id %s was not found.".formatted(id));
    }
}
