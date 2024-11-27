package com.api.v2.medical_appointment.domain

import org.bson.types.ObjectId
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface MedicalAppointmentRepository: CoroutineCrudRepository<MedicalAppointment, ObjectId>