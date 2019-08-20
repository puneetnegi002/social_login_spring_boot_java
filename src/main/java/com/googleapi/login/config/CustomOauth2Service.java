package com.googleapi.login.config;

import com.googleapi.login.domain.AuthProvider;
import com.googleapi.login.domain.OAuth2UserInfo;
import com.googleapi.login.domain.OAuth2UserInfoFactory;
import com.googleapi.login.domain.User;
import com.googleapi.login.exception.OAuth2Exception;
import com.googleapi.login.service.UserService;
import com.googleapi.login.util.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Service
public class CustomOauth2Service extends DefaultOAuth2UserService {

  private UserService userService;

  @Autowired
  public CustomOauth2Service(UserService userService) {
    this.userService = userService;
  }

  @Override
  public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

    OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
    if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
      throw new OAuth2Exception("Email not found");
    }

    User dbUser = userService.findByEmail(oAuth2UserInfo.getEmail(), AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()).toString());
    User user = new User();
    if (Objects.isNull(dbUser)) {
      user.setEmail(oAuth2UserInfo.getEmail());
      user.setImageUrl(oAuth2UserInfo.getImageUrl());
      user.setName(oAuth2UserInfo.getName());
      user.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
      user.setProviderId(oAuth2UserInfo.getId());
      userService.save(user);
      AuthenticationUtil.getAuthentication(user);
    }
    AuthenticationUtil.getAuthentication(user);
    return oAuth2User;
  }

}
