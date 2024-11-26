package com.api.v2.doctors.domain

import com.api.v2.doctors.dtos.MedicalLicenseNumberDto
import com.api.v2.people.domain.Person
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.time.ZoneId

@Document
class Doctor(
    var medicalLicenseNumberDto: MedicalLicenseNumberDto,
    var person: Person
) {

    @BsonId
    var id: ObjectId = ObjectId()
    val createdAt: LocalDateTime = LocalDateTime.now()
    val createdAtZone: ZoneId = ZoneId.systemDefault()

    companion object {
        fun create( medicalLicenseNumberDto: MedicalLicenseNumberDto, person: Person): Doctor {
            return Doctor(medicalLicenseNumberDto, person)
        }
    }

}