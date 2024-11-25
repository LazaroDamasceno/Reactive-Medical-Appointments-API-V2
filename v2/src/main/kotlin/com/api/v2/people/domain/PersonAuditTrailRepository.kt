package com.api.v2.people.domain

import org.bson.types.ObjectId
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface PersonAuditTrailRepository: CoroutineCrudRepository<PersonAuditTrail, ObjectId>
