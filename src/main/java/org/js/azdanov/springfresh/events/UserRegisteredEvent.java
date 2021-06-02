package org.js.azdanov.springfresh.events;

import java.util.Locale;
import lombok.Getter;
import org.js.azdanov.springfresh.dtos.UserDTO;
import org.springframework.context.ApplicationEvent;

@Getter
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
}
