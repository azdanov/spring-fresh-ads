package org.js.azdanov.springfresh.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class LoginController {
  @GetMapping("/login")
  public String login(
      HttpServletRequest request,
      @AuthenticationPrincipal UserDetails userDetails,
      @RequestParam(required = false) String error) {
    if (error != null) {
      logError(request);
    }

    if (userDetails == null) {
      return "auth/login";
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
        log.warn(ex.getLocalizedMessage());
      }
    }
  }
}
