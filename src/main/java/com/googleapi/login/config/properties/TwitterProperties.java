package com.googleapi.login.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "twitter.client")
public class TwitterProperties {
  private String clientId;
  private String clientSecret;
  private String emailRequestUri;
  private String redirectUri;

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getClientSecret() {
    return clientSecret;
  }

  public void setClientSecret(String clientSecret) {
    this.clientSecret = clientSecret;
  }

  public String getEmailRequestUri() {
    return emailRequestUri;
  }

  public void setEmailRequestUri(String emailRequestUri) {
    this.emailRequestUri = emailRequestUri;
  }

  public String getRedirectUri() {
    return redirectUri;
  }

  public void setRedirectUri(String redirectUri) {
    this.redirectUri = redirectUri;
  }
}
