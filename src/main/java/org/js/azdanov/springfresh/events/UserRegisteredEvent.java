package org.js.azdanov.springfresh.events;

import org.js.azdanov.springfresh.dtos.UserDTO;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class UserRegisteredEvent extends ApplicationEvent {

  private final UserDTO userDTO;
  private final String confirmationURI;
  private final Locale locale;

  public UserRegisteredEvent(UserDTO userDTO, Locale locale, String confirmationURI) {
    super(userDTO);
    this.userDTO = userDTO;
    this.confirmationURI = confirmationURI;
    this.locale = locale;
  }

  public UserDTO getUserDTO() {
    return userDTO;
  }

  public String getConfirmationURI() {
    return confirmationURI;
  }

  public Locale getLocale() {
    return locale;
  }
}
