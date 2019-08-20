package com.googleapi.login.exception;

public class OAuth2Exception extends RuntimeException {

  public OAuth2Exception() {
  }

  public OAuth2Exception(String s) {
    super(s);
  }

  public OAuth2Exception(String s, Throwable throwable) {
    super(s, throwable);
  }

  public OAuth2Exception(Throwable throwable) {
    super(throwable);
  }

  public OAuth2Exception(String s, Throwable throwable, boolean b, boolean b1) {
    super(s, throwable, b, b1);
  }
}
