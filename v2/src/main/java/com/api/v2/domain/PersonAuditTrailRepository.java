package com.api.v2.domain;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PersonAuditTrailRepository extends ReactiveMongoRepository<PersonAuditTrail, ObjectId> {
}
