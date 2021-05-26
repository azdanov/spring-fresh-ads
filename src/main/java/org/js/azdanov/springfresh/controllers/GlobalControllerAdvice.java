package org.js.azdanov.springfresh.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Locale;

@ControllerAdvice
public class GlobalControllerAdvice {

  @ModelAttribute("locale")
  public Locale getLocale(Locale locale) {
    return locale;
  }
}
