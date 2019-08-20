package com.googleapi.login.repository.impl;

import com.googleapi.login.domain.User;
import com.googleapi.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

  private MongoTemplate mongoTemplate;

  @Autowired
  public UserRepositoryImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public User save(User user) {
    mongoTemplate.insert(user);
    return user;
  }

  @Override
  public User findByEmail(String email, String provider) {
    return mongoTemplate
            .findOne(new Query(Criteria.where("email").is(email).and("provider").is(provider)), User.class);
  }
}
