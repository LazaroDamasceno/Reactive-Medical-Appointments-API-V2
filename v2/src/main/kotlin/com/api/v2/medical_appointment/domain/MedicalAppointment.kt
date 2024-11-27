package com.api.v2.medical_appointment.domain

import com.api.v2.customers.domain.Customer
import com.api.v2.doctors.domain.Doctor
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.time.ZoneId

@Document
class MedicalAppointment(
    val type: String,
    val customer: Customer,
    val doctor: Doctor,
    val bookingDate: LocalDateTime
) {

    @BsonId
    var id: ObjectId = ObjectId()
    val bookedAt: ZoneId = ZoneId.systemDefault()
    var cancelingDate: LocalDateTime? = null
    var canceledAt: ZoneId? = null
    var completionDate: LocalDateTime? = null
    var completedAt: ZoneId? = null
    val createdAt: LocalDateTime = LocalDateTime.now()
    val createdAtZone: ZoneId = ZoneId.systemDefault()

    companion object {
        fun create(
            type: String,
            customer: Customer,
            doctor: Doctor,
            bookingDate: LocalDateTime
        ): MedicalAppointment {
            return MedicalAppointment(
                type,
                customer,
                doctor,
                bookingDate
            )
        }
    }

    fun markAsCompleted() {
        completionDate = LocalDateTime.now()
        completedAt = ZoneId.systemDefault()
    }

    fun markAsCanceled() {
        cancelingDate = LocalDateTime.now()
        canceledAt = ZoneId.systemDefault()
    }

}