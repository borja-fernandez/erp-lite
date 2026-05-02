package com.borfer.erp_lite.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.borfer.erp_lite.persistence.mongo.repository")
public class MongoConfiguration {
}