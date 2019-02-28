package com.ari.msexp1.mongoDAL.DAL;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

@Configuration
public class AlarmDALDbConfig extends AbstractMongoConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(AlarmDALDbConfig.class);

    @Override
    protected String getDatabaseName() {
        return "test";
    }

    @Override
    public MongoClient mongoClient() {
        String uriStr = System.getenv("MONGODB_URI");
        if (uriStr == null) {
           uriStr = "mongodb";
        }
        logger.info("Creating mongo client - "+uriStr);
        MongoClientURI uri = new MongoClientURI(uriStr);
        return new MongoClient(uri);
    }
}
