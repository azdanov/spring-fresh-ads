package org.js.azdanov.springfresh.exceptions;

public final class ListingNotFoundException extends RuntimeException {
  public ListingNotFoundException() {
    super();
  }

  public ListingNotFoundException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public ListingNotFoundException(final String message) {
    super(message);
  }

  public ListingNotFoundException(final Throwable cause) {
    super(cause);
  }
}
