package com.contactsunny.poc.MongoDBSpringBootEncryptionDecryptionPOC.repositories;

import com.contactsunny.poc.MongoDBSpringBootEncryptionDecryptionPOC.models.CascadingPOC;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CascadingPOCRepository extends MongoRepository<CascadingPOC, String> {
}
