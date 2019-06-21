package com.contactsunny.poc.MongoDBSpringBootCascadingPOC.eventListeners;

import com.contactsunny.poc.MongoDBSpringBootCascadingPOC.utils.EncryptionUtil;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterLoadEvent;

import java.util.Arrays;
import java.util.List;

public class MongoDBAfterLoadEventListener extends AbstractMongoEventListener<Object> {

    private static Logger logger = LogManager.getLogger(MongoDBAfterLoadEventListener.class);

    @Autowired
    private EncryptionUtil encryptionUtil;

    @Override
    public void onAfterLoad(AfterLoadEvent<Object> event) {

        Document eventObject = event.getDocument();

        /*
        We'll skip these because:
        * We don't want to encrypt the ID as we'll be querying on this most of the times in our business logic.
        * The '_class' field is a meta-data field added by SpringBoot which really is not very important in terms of
            being encrypted or not.
         */
        List<String> keysNotToEncrypt = Arrays.asList("_class", "_id");

        for ( String key : eventObject.keySet() ) {
            if (!keysNotToEncrypt.contains(key)) {
                eventObject.put(key, this.encryptionUtil.decrypt(eventObject.get(key).toString()));
            }
        }

        logger.info("DB Object: " + new Gson().toJson(eventObject));

        super.onAfterLoad(event);
    }
}
