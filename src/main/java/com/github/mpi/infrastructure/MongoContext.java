package com.github.mpi.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.Mongo;

@Configuration
@Profile("mongo")
public class MongoContext {

    @Bean
    public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory) throws Exception {
        return new MongoTemplate(mongoDbFactory);
    }

    @Bean
    protected MongoDbFactory mongoDbFactory(
            @Value("${mongo.db.url}") String host, 
            @Value("${mongo.db.port}") int port,
            @Value("${mongo.db.name}") String databaseName, 
            @Value("${mongo.db.user}") String username, 
            @Value("${mongo.db.pass}") String password) throws Exception {
        
        return new SimpleMongoDbFactory(new Mongo(host, port), databaseName, new UserCredentials(username, password));
    }
}
