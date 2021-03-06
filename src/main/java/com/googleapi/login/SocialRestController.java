package com.googleapi.login;

import com.googleapi.login.domain.AuthProvider;
import com.googleapi.login.service.SocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

   /* System.out.println("lkh");*/
    System.out.println(a);
  }

  @GetMapping(value = "/demo")
  public String authhg(HttpServletResponse response, HttpServletRequest request, @RequestParam("code") String a) throws IOException {
return "welcome";
  }

}
