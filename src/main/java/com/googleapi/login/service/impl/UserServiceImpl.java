package com.googleapi.login.service.impl;

import com.googleapi.login.domain.User;
import com.googleapi.login.exception.UserNotFoundException;
import com.googleapi.login.repository.UserRepository;
import com.googleapi.login.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User save(User user) {
    if (Objects.isNull(user)) {
      throw new UserNotFoundException();
    }
    return userRepository.save(user);
  }

  @Override
  public User findByEmail(String email, String provider) {
    if(StringUtils.isBlank(email)) {
      throw new UserNotFoundException("Email must not be empty");
    }
    return userRepository.findByEmail(email, provider);
  }
}
