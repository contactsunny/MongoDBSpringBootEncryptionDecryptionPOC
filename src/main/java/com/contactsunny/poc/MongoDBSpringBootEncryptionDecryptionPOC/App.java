package com.contactsunny.poc.MongoDBSpringBootEncryptionDecryptionPOC;

import com.contactsunny.poc.MongoDBSpringBootEncryptionDecryptionPOC.eventListeners.MongoDBAfterLoadEventListener;
import com.contactsunny.poc.MongoDBSpringBootEncryptionDecryptionPOC.eventListeners.MongoDBBeforeSaveEventListener;
import com.contactsunny.poc.MongoDBSpringBootEncryptionDecryptionPOC.models.CascadingPOC;
import com.contactsunny.poc.MongoDBSpringBootEncryptionDecryptionPOC.repositories.CascadingPOCRepository;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
public class App implements CommandLineRunner {

    @Autowired
    private CascadingPOCRepository cascadingPOCRepository;

    private static Logger logger = LogManager.getLogger(App.class);

    /*
    We register a bean of type MongoDBBeforeSaveEventListener. This will make sure that the MongoDB event
    listener will be called by SpringBoot just before a save operation is about to happen.
    In this listener, we'll encrypt all the values in the document before it is being stored to the database.
     */
    @Bean
    public MongoDBBeforeSaveEventListener mongoDBBeforeSaveEventListener() {
        return new MongoDBBeforeSaveEventListener();
    }

    /*
    We register a bean of type MongoDBAfterLoadEventListener. This will make sure that the MongoDB event
    listener will be called by springBoot right after a load operation has happened.
    In this listener, we'll decrypt the values in the document loaded before it mapped to an object.
     */
    @Bean
    public MongoDBAfterLoadEventListener mongoDBAfterLoadEventListener() {
        return new MongoDBAfterLoadEventListener();
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {

        long timeStamp = System.currentTimeMillis();

        CascadingPOC cascadingPOC = new CascadingPOC();
        cascadingPOC.setName("Test name - " + timeStamp);
        cascadingPOC.setDescription("Test description - " + timeStamp);

        cascadingPOC = cascadingPOCRepository.save(cascadingPOC);

        logger.info("ID of saved object: " + cascadingPOC.getId());

        Optional<CascadingPOC> fetchedObject = cascadingPOCRepository.findById(cascadingPOC.getId());

        logger.info("Fetched object: " + new Gson().toJson(fetchedObject.get()));
    }
}
