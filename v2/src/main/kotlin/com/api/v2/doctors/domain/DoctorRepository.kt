package com.api.v2.doctors.domain

import org.bson.types.ObjectId
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface DoctorRepository: CoroutineCrudRepository<Doctor, ObjectId>