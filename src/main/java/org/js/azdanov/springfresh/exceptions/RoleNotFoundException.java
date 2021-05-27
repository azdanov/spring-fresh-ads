package org.js.azdanov.springfresh.exceptions;

public final class RoleNotFoundException extends RuntimeException {
  public RoleNotFoundException() {
    super();
  }

  public RoleNotFoundException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public RoleNotFoundException(final String message) {
    super(message);
  }

  public RoleNotFoundException(final Throwable cause) {
    super(cause);
  }
}
