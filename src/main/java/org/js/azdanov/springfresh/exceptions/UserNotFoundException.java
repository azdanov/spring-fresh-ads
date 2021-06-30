package org.js.azdanov.springfresh.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public final class UserNotFoundException extends RuntimeException {
  public UserNotFoundException() {
    super();
  }

  public UserNotFoundException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public UserNotFoundException(final String message) {
    super(message);
  }

  public UserNotFoundException(final Throwable cause) {
    super(cause);
  }
}
