package org.js.azdanov.springfresh.services;

import org.js.azdanov.springfresh.dtos.ListingDTO;

public interface StripePaymentService {
  String createIntent(ListingDTO listing);

  void handleWebhook(String payload, String stripeSignature);
}
