package com.googleapi.login.config;

import com.googleapi.login.config.properties.MongoProperties;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig extends AbstractMongoConfiguration {

  private MongoProperties mongoProperties;

  @Autowired
  public MongoConfig(MongoProperties mongoProperties) {
    this.mongoProperties = mongoProperties;
  }

  @Override
  @Bean
  public MongoClient mongoClient() {
    return new MongoClient(new MongoClientURI(mongoProperties.getUri()));
  }

  @Override
  protected String getDatabaseName() {
    return mongoProperties.getDatabase();
  }

  @Bean
  public MongoTemplate mongoTemplate() throws Exception {
    return new MongoTemplate(mongoClient(), getDatabaseName());
  }

}

