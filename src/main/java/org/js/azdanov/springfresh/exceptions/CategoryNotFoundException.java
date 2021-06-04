package org.js.azdanov.springfresh.exceptions;

public final class CategoryNotFoundException extends RuntimeException {
  public CategoryNotFoundException() {
    super();
  }

  public CategoryNotFoundException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public CategoryNotFoundException(final String message) {
    super(message);
  }

  public CategoryNotFoundException(final Throwable cause) {
    super(cause);
  }
}
