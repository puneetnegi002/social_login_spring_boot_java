package com.googleapi.login.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private static final Logger LOGGER = LogManager.getLogger(CustomAuthenticationSuccessHandler.class);
  @Override

  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    if (response.isCommitted()) {
      LOGGER.debug("Response has already been committed. Unable to redirect to ");
      return;
    }
    super.clearAuthenticationAttributes(request);
    response.sendRedirect("http://localhost:8080/login");
  }

}
