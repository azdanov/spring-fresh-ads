package org.js.azdanov.springfresh.services;

import org.js.azdanov.springfresh.dtos.ListingDTO;

public interface ContactService {
  void sendMessage(ListingDTO listing, String message, String senderEmail, String listingURI);
}
