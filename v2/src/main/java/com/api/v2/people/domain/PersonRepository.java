package com.api.v2.people.domain;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PersonRepository extends ReactiveMongoRepository<Person, ObjectId> {
}
