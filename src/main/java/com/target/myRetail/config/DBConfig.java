package com.target.myRetail.config;




import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class DBConfig {

    @Bean
    public MongoDatabase mongoDB() {
        MongoClient mongoClient = MongoClients.create
        ("mongodb+srv://sabari:sabari@cluster0.mvkfa.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
        return mongoClient.getDatabase("myretail");
    }

}
