package com.googleapi.login.service;

public interface SocialService {

  String getTwitterSignInUrl();

  void getTwitterUserDetails(String oAuthVerifier);
}
