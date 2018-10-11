package com.vegesoft.moneytracker.configuration;

import com.mongodb.MongoClientURI;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableReactiveMongoRepositories
public class MongoReactiveConfiguration extends AbstractReactiveMongoConfiguration {

    private final String mongoDbUri;

    public MongoReactiveConfiguration(@Value("${spring.data.mongodb.uri}") final String mongoDbUri) {
        this.mongoDbUri = mongoDbUri;
    }

    @Override
    public MongoClient reactiveMongoClient() {
        return MongoClients.create(clientURI().getURI());
    }

    @Override
    protected String getDatabaseName() {
        return clientURI().getDatabase();
    }

    private MongoClientURI clientURI() {
        return new MongoClientURI(mongoDbUri);
    }
}
