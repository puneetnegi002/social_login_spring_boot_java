package com.googleapi.login.repository;

import com.googleapi.login.domain.User;

public interface UserRepository {
  User save(User user);

  User findByEmail(String email, String provider);
}
