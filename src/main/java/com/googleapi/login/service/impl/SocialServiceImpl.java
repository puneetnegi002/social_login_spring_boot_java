package com.googleapi.login.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.googleapi.login.config.properties.TwitterProperties;
import com.googleapi.login.domain.AuthProvider;
import com.googleapi.login.domain.User;
import com.googleapi.login.exception.UserNotFoundException;
import com.googleapi.login.service.SocialService;
import com.googleapi.login.service.UserService;
import com.googleapi.login.util.AuthenticationUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Service
public class SocialServiceImpl implements SocialService {

  private TwitterProperties twitterProperties;
  private UserService userService;
  private OAuthToken requestToken;

  @Autowired
  public SocialServiceImpl(TwitterProperties twitterProperties, UserService userService) {
    this.twitterProperties = twitterProperties;
    this.userService = userService;
  }

  @Override
  public String getTwitterSignInUrl() {

    TwitterConnectionFactory connectionFactory = getTwitterConnection();
    OAuth1Operations oauthOperations = connectionFactory.getOAuthOperations();
    requestToken = oauthOperations.fetchRequestToken(twitterProperties.getRedirectUri(), null);
    return oauthOperations.buildAuthorizeUrl(requestToken.getValue(), OAuth1Parameters.NONE);
  }

  @Override
  public void getTwitterUserDetails(String oAuthVerifier) {
    TwitterConnectionFactory connectionFactory = getTwitterConnection();
    OAuth1Operations oauthOperations = connectionFactory.getOAuthOperations();
    OAuthToken accessToken = oauthOperations.exchangeForAccessToken(new AuthorizedRequestToken(requestToken, oAuthVerifier), null);
    Connection<Twitter> twitterConnection = connectionFactory.createConnection(accessToken);

    TwitterTemplate twitterTemplate;
    twitterTemplate = new TwitterTemplate(twitterProperties.getClientId(),
            twitterProperties.getClientSecret(), twitterConnection.createData().getAccessToken(),
            twitterConnection.createData().getSecret());

    RestTemplate restTemplate = twitterTemplate.getRestTemplate();
    String response = restTemplate.getForObject(twitterProperties.getEmailRequestUri(), String.class);

    ObjectMapper mapper = new ObjectMapper();
    User user = new User();
    try {
      Map<String, Object> map = mapper.readValue(response, Map.class);
      String email = map.getOrDefault("email", StringUtils.EMPTY).toString();
      User dbUser = userService.findByEmail(email, AuthProvider.twitter.toString());
      if(Objects.isNull(dbUser)) {
        user.setEmail(email);
        user.setName(twitterConnection.fetchUserProfile().getName());
        user.setProviderId(twitterConnection.getKey().getProviderUserId());
        user.setProvider(AuthProvider.twitter);
        user.setImageUrl(twitterConnection.getImageUrl());
        userService.save(user);
        AuthenticationUtil.getAuthentication(user);
      } else {
        AuthenticationUtil.getAuthentication(dbUser);
      }
    } catch (IOException e) {
      throw new UserNotFoundException();
    }
  }

  private TwitterConnectionFactory getTwitterConnection() {
    return new TwitterConnectionFactory(twitterProperties.getClientId(),
            twitterProperties.getClientSecret());
  }
}
