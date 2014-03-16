package com.github.mpi.infrastructure.mongo;

import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("mongo-lab")
public class MongoLabProfile {

    @Bean
    public PropertyPlaceholderConfigurer developmentPreferences(){
        
        PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
        configurer.setProperties(mongoLabProperties());
        return configurer;
    }

    private Properties mongoLabProperties() {

        Properties properties = new Properties();
        properties.setProperty("mongo.db.url", "ds031359.mongolab.com");
        properties.setProperty("mongo.db.port", "31359");
        properties.setProperty("mongo.db.name", "heroku_app23013898");
        properties.setProperty("mongo.db.user", "open-trapp");
        properties.setProperty("mongo.db.pass", "open-trapp");
        
        return properties;
    }
    
}
