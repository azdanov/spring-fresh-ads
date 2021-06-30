package org.js.azdanov.springfresh.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public final class StripeWebhookException extends RuntimeException {
  public StripeWebhookException() {
    super();
  }

  public StripeWebhookException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public StripeWebhookException(final String message) {
    super(message);
  }

  public StripeWebhookException(final Throwable cause) {
    super(cause);
  }
}
