package org.js.azdanov.springfresh.controllers;

import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.js.azdanov.springfresh.dtos.ListingDTO;
import org.js.azdanov.springfresh.exceptions.ForbiddenException;
import org.js.azdanov.springfresh.services.ListingService;
import org.js.azdanov.springfresh.services.StripePaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class PaymentController {
  public final ListingService listingService;
  public final StripePaymentService stripePaymentService;

  @Value("${stripe.public-key}")
  String publicKey;

  @GetMapping("/listings/{listingId}/payment")
  public String show(
      @PathVariable Integer listingId,
      @AuthenticationPrincipal UserDetails userDetails,
      Model model) {

    var listing = listingService.getById(listingId);

    canContinue(listingId, userDetails, listing);

    if (isPaid(listing)) {
      String clientSecret = stripePaymentService.createIntent(listing);
      model.addAttribute("clientSecret", clientSecret);
    }

    model.addAttribute("listing", listing);
    model.addAttribute("publicKey", publicKey);

    return "listings/payment/index";
  }

  private boolean isPaid(ListingDTO listing) {
    return listing.category().price().compareTo(BigDecimal.ZERO) > 0;
  }

  @PatchMapping("/listings/{listingId}/payment")
  public String update(
      @PathVariable Integer listingId, @AuthenticationPrincipal UserDetails userDetails) {
    var listing = listingService.getById(listingId);

    canContinue(listingId, userDetails, listing);

    if (isPaid(listing)) {
      return "redirect:/listings/%d/payment".formatted(listingId);
    }

    listingService.handleFreeListing(listingId);
    return "redirect:/listings/{listingId}/edit";
  }

  private void canContinue(Integer listingId, UserDetails userDetails, ListingDTO listing) {
    Assert.state(!listing.live(), "listing is already live");
    Assert.state(listing.payment() == null, "listing is already paid");

    if (!listing.user().email().equals(userDetails.getUsername())) {
      throw new ForbiddenException(
          "show payment Listing(%d, %s) by User(%s)"
              .formatted(listingId, listing.user().email(), userDetails.getUsername()));
    }
  }

  @PostMapping("/webhook/stripe/events")
  @ResponseBody
  public ResponseEntity<String> stripeWebhookStore(
      @RequestBody String payload, @RequestHeader("Stripe-Signature") String stripeSignature) {
    stripePaymentService.handleWebhook(payload, stripeSignature);

    return ResponseEntity.ok().build();
  }
}
