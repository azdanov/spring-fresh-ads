package org.js.azdanov.springfresh.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public final class UserAlreadyExistsException extends RuntimeException {
  public UserAlreadyExistsException() {
    super();
  }

  public UserAlreadyExistsException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public UserAlreadyExistsException(final String message) {
    super(message);
  }

  public UserAlreadyExistsException(final Throwable cause) {
    super(cause);
  }
}
