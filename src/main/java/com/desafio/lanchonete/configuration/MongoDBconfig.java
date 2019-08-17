package com.desafio.lanchonete.configuration;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@EnableMongoAuditing
public class MongoDBconfig extends AbstractMongoConfiguration {

    private static final String DATABASE_NAME = "pedidos";

    private String host;

    private int port;

    public MongoDBconfig(@Value("${spring.data.mongodb.host:localhost}") String host,
                         @Value("${spring.data.mongodb.port:27017}") int port){
        this.host = host;
        this.port = port;
    }

    @Override
    public MongoClient mongoClient(){
        return new MongoClient(host, port);
    }

    @Override
    protected String getDatabaseName(){
        return DATABASE_NAME;
    }
}
