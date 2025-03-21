package com.api.v2.medical_slots.domain;

import com.api.v2.common.DstCheckerUtil;
import com.api.v2.doctors.domain.Doctor;
import com.api.v2.medical_appointments.domain.MedicalAppointment;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.UUID;

@Document
public class MedicalSlot {

    @Id
    private String id;
    private Doctor doctor;
    private LocalDateTime availableAt;
    private ZoneId availableAtZoneId;
    private ZoneOffset availableAtZoneOffset;
    private boolean isAvailableDuringDST;
    private MedicalAppointment medicalAppointment;
    private LocalDateTime createdAt;
    private ZoneId createdAtZoneId;
    private ZoneOffset createdAtZoneOffset;
    private boolean isCreatedDuringDST;
    private LocalDateTime canceledAt;
    private ZoneId canceledAtZoneId;
    private ZoneOffset canceledAtZoneOffset;
    private Boolean isCanceledDuringDST;
    private LocalDateTime completedAt;
    private ZoneId completedAtZoneId;
    private ZoneOffset completedAtZoneOffset;
    private Boolean isCompletedDuringDst;

    public MedicalSlot() {
    }

    private MedicalSlot(Doctor doctor,
                        LocalDateTime availableAt,
                        ZoneId availableAtZoneId,
                        ZoneOffset availableAtZoneOffset
    ) {
        this.id = UUID.randomUUID().toString();
        this.doctor = doctor;
        this.availableAt = availableAt;
        this.availableAtZoneId = availableAtZoneId;
        this.availableAtZoneOffset = availableAtZoneOffset;
        this.isAvailableDuringDST = DstCheckerUtil.isGivenDateTimeFollowingDST(availableAt, availableAtZoneId);
        this.createdAt = LocalDateTime.now(ZoneId.systemDefault());
        this.createdAtZoneId = ZoneId.systemDefault();
        this.createdAtZoneOffset = OffsetDateTime.now().getOffset();
        this.isCreatedDuringDST = DstCheckerUtil.isGivenDateTimeFollowingDST(LocalDateTime.now(), ZoneId.systemDefault());
    }

    public static MedicalSlot of(Doctor doctor,
                                     LocalDateTime availableAt,
                                     ZoneId availableAtZoneId,
                                     ZoneOffset availableAtZoneOffset
    ) {
        return new MedicalSlot(doctor, availableAt, availableAtZoneId, availableAtZoneOffset);
    }

    public void markAsCanceled() {
        canceledAt = LocalDateTime.now(ZoneId.systemDefault());
        canceledAtZoneId = ZoneId.systemDefault();
        canceledAtZoneOffset = OffsetDateTime.now().getOffset();
        isCanceledDuringDST = DstCheckerUtil.isGivenDateTimeFollowingDST(canceledAt, canceledAtZoneId);
        medicalAppointment = null;
    }

    public void markAsCompleted(MedicalAppointment completedMedicalAppointment) {
        completedAt = LocalDateTime.now(ZoneId.systemDefault());
        completedAtZoneId = ZoneId.systemDefault();
        completedAtZoneOffset = OffsetDateTime.now().getOffset();
        isCompletedDuringDst = DstCheckerUtil.isGivenDateTimeFollowingDST(completedAt, canceledAtZoneId);
        medicalAppointment = completedMedicalAppointment;
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

    public ZoneOffset getAvailableAtZoneOffset() {
        return availableAtZoneOffset;
    }

    public ZoneOffset getCreatedAtZoneOffset() {
        return createdAtZoneOffset;
    }

    public ZoneOffset getCanceledAtZoneOffset() {
        return canceledAtZoneOffset;
    }

    public ZoneOffset getCompletedAtZoneOffset() {
        return completedAtZoneOffset;
    }

    public boolean isCreatedDuringDST() {
        return isCreatedDuringDST;
    }

    public boolean isCanceledDuringDST() {
        return isCanceledDuringDST;
    }

    public boolean isCompletedDuringDst() {
        return isCompletedDuringDst;
    }

    public boolean isAvailableDuringDST() {
        return isAvailableDuringDST;
    }
}
