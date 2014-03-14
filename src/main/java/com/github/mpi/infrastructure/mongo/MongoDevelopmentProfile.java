package com.github.mpi.infrastructure.mongo;

import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("mongo-dev")
public class MongoDevelopmentProfile {

    @Bean
    public PropertyPlaceholderConfigurer developmentPreferences(){
        
        PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
        configurer.setProperties(developmentProperties());
        return configurer;
    }

    private Properties developmentProperties() {

        Properties properties = new Properties();
        properties.setProperty("mongo.db.url", "localhost");
        properties.setProperty("mongo.db.port", "27017");
        properties.setProperty("mongo.db.name", "open-trapp");
        properties.setProperty("mongo.db.user", "");
        properties.setProperty("mongo.db.pass", "");
        
        return properties;
    }
    
}
