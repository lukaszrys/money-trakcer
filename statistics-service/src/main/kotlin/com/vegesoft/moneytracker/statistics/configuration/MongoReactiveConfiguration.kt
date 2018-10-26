package com.vegesoft.moneytracker.statistics.configuration

import com.mongodb.MongoClientURI
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@EnableReactiveMongoRepositories
class MongoReactiveConfiguration(@Value("\${spring.data.mongodb.uri}") val mongoDbUri: String) : AbstractReactiveMongoConfiguration() {

    override fun reactiveMongoClient(): MongoClient {
        return MongoClients.create(clientURI().uri)
    }

    override fun getDatabaseName(): String {
        return clientURI().database
    }

    private fun clientURI(): MongoClientURI {
        return MongoClientURI(mongoDbUri)
    }
}