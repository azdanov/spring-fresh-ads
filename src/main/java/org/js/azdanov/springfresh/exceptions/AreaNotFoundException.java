package org.js.azdanov.springfresh.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public final class AreaNotFoundException extends RuntimeException {
  public AreaNotFoundException() {
    super();
  }

  public AreaNotFoundException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public AreaNotFoundException(final String message) {
    super(message);
  }

  public AreaNotFoundException(final Throwable cause) {
    super(cause);
  }
}
