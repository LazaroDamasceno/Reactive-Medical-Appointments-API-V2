package com.api.v2.medical_slots.domain;

import com.api.v2.doctors.domain.Doctor;
import com.api.v2.medical_appointments.domain.MedicalAppointment;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Document
public class MedicalSlot {

    @BsonId
    private ObjectId id;
    private Doctor doctor;
    private LocalDateTime availableAt;
    private ZoneId availableAtZoneId;
    private MedicalAppointment medicalAppointment;
    private LocalDateTime createdAt;
    private ZoneId createdAtZoneId;
    private LocalDateTime canceledAt;
    private ZoneId canceledAtZoneId;
    private LocalDateTime completedAt;
    private ZoneId completedAtZoneId;

    private MedicalSlot(Doctor doctor, LocalDateTime availableAt) {
        this.id = new ObjectId();
        this.doctor = doctor;
        this.availableAt = availableAt;
        this.availableAtZoneId = ZoneId.systemDefault();
        this.createdAt = LocalDateTime.now(ZoneId.systemDefault());
        this.createdAtZoneId = ZoneId.systemDefault();
    }

    public MedicalSlot() {
    }

    public static MedicalSlot create(Doctor doctor, LocalDateTime availableAt) {
        return new MedicalSlot(doctor, availableAt);
    }

    public void markAsCanceled() {
        canceledAt = LocalDateTime.now(ZoneId.systemDefault());
        canceledAtZoneId = ZoneId.systemDefault();
    }

    public void markAsCompleted() {
        completedAt = LocalDateTime.now(ZoneId.systemDefault());
        completedAtZoneId = ZoneId.systemDefault();
    }

    public ObjectId getId() {
        return id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public LocalDateTime getAvailableAt() {
        return availableAt;
    }

    public ZoneId getAvailableAtZoneId() {
        return availableAtZoneId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public ZoneId getCreatedAtZoneId() {
        return createdAtZoneId;
    }

    public MedicalAppointment getMedicalAppointment() {
        return medicalAppointment;
    }

    public LocalDateTime getCanceledAt() {
        return canceledAt;
    }

    public ZoneId getCanceledAtZoneId() {
        return canceledAtZoneId;
    }

    public void setMedicalAppointment(MedicalAppointment medicalAppointment) {
        this.medicalAppointment = medicalAppointment;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public ZoneId getCompletedAtZoneId() {
        return completedAtZoneId;
    }
}
