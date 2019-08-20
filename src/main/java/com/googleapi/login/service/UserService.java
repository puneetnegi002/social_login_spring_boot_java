package com.googleapi.login.service;

import com.googleapi.login.domain.User;

public interface UserService {

  User save(User user);

  User findByEmail(String email, String provider);
}
