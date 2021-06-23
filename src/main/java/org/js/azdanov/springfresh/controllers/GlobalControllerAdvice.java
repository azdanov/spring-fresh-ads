package org.js.azdanov.springfresh.controllers;

import lombok.extern.slf4j.Slf4j;
import org.js.azdanov.springfresh.exceptions.ForbiddenException;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalControllerAdvice {
  @InitBinder
  public void initBinder(WebDataBinder binder) {
    StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(false);
    binder.registerCustomEditor(String.class, stringTrimmerEditor);
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String handle(IllegalStateException e) {
    return "error/400";
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public String handle(ForbiddenException e) {
    return "error/403";
  }
}
