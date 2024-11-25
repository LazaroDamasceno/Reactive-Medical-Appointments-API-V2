package com.api.v2.people.domain

import java.time.LocalDateTime
import java.time.ZoneId

data class PersonAuditTrail(
    val person: Person,
    val createdAt: LocalDateTime,
    val createdAtZone: ZoneId
) {
    companion object {
        fun create(person: Person): PersonAuditTrail {
            return PersonAuditTrail(
                person,
                LocalDateTime.now(),
                ZoneId.systemDefault())
        }
    }
}
