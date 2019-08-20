package com.googleapi.login;

import com.googleapi.login.domain.AuthProvider;
import com.googleapi.login.service.SocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.social.connect.Connection;
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class SocialRestController {

  private SocialService socialService;

  @Autowired
  public SocialRestController(SocialService socialService) {
    this.socialService = socialService;
  }

  @GetMapping(value = "/login/{provider}")
  public void login(@PathVariable String provider, HttpServletRequest request, HttpServletResponse response) throws IOException {
    if (provider.toLowerCase().equals(AuthProvider.instagram.toString())) {
      response.sendRedirect(socialService.getTwitterSignInUrl());
    } else {
      response.sendRedirect(request.getContextPath().concat("/oauth2/authorization/".concat(provider.toLowerCase())));
    }
  }


  @GetMapping(value = "/authorize/twitter")
  public void auth(HttpServletResponse response, @RequestParam("oauth_verifier") String oauthVerifier,
                   @RequestParam("oauth_token") String oauthToken) throws IOException {
    socialService.getTwitterUserDetails(oauthVerifier);
  }


  @GetMapping(value = "/authorize/instagram")
  public void authh(HttpServletResponse response,HttpServletRequest request,@RequestParam("code") String a) throws IOException {

    System.out.println("lkh");
    System.out.println(a);
  }

}
