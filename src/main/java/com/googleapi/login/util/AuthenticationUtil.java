package com.googleapi.login.util;

import com.googleapi.login.domain.OAuth2UserInfo;
import com.googleapi.login.domain.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationUtil {

  public static void getAuthentication(User user) {
    UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(user.getEmail(), null, null);
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }
}
