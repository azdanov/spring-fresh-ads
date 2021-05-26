package org.js.azdanov.springfresh.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
  private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

  @GetMapping("/login")
  public String login(
      HttpServletRequest request,
      @AuthenticationPrincipal UserDetails userDetails,
      @RequestParam(required = false) String error) {
    if (error != null) {
      logError(request);
    }

    if (userDetails == null) {
      return "login";
    } else {
      return "redirect:/";
    }
  }

  private void logError(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session != null) {
      AuthenticationException ex =
          (AuthenticationException) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
      if (ex != null) {
        LOG.warn(ex.getLocalizedMessage());
      }
    }
  }
}
