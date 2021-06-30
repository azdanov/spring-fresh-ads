package org.js.azdanov.springfresh.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public final class ForbiddenException extends RuntimeException {
  public ForbiddenException() {
    super();
  }

  public ForbiddenException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public ForbiddenException(final String message) {
    super(message);
  }

  public ForbiddenException(final Throwable cause) {
    super(cause);
  }
}
