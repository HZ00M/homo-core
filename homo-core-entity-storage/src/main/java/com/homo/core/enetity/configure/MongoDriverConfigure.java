package com.homo.core.enetity.configure;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Slf4j
@Configuration
@Component
public class MongoDriverConfigure {
    @Autowired
    MongoDriverProperties mongoDriverProperties;
    @Bean
    public MongoClient mongoClient() {
        log.info("MongoClient post construct");
        log.info("connString: {}\ndatabase: {}", mongoDriverProperties.getConnString(), mongoDriverProperties.getDatabase());

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(mongoDriverProperties.getConnString()))
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        return mongoClient;
    }
}
