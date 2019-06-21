package com.contactsunny.poc.MongoDBSpringBootCascadingPOC.repositories;

import com.contactsunny.poc.MongoDBSpringBootCascadingPOC.models.CascadingPOC;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CascadingPOCRepository extends MongoRepository<CascadingPOC, String> {
}
