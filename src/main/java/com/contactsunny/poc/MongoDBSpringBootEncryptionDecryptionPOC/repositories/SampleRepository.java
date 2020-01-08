package com.contactsunny.poc.MongoDBSpringBootEncryptionDecryptionPOC.repositories;

import com.contactsunny.poc.MongoDBSpringBootEncryptionDecryptionPOC.models.Sample;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SampleRepository extends MongoRepository<Sample, String> {
}
