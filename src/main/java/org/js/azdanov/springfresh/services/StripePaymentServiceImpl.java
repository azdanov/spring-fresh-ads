package org.js.azdanov.springfresh.services;

import static org.js.azdanov.springfresh.config.CacheConfig.CATEGORY_LISTING;

import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.net.Webhook;
import com.stripe.param.PaymentIntentCreateParams;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.annotation.PostConstruct;
import javax.cache.CacheManager;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.js.azdanov.springfresh.dtos.ListingDTO;
import org.js.azdanov.springfresh.exceptions.ListingNotFoundException;
import org.js.azdanov.springfresh.exceptions.StripeWebhookException;
import org.js.azdanov.springfresh.models.Payment;
import org.js.azdanov.springfresh.repositories.ListingRepository;
import org.js.azdanov.springfresh.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class StripePaymentServiceImpl implements StripePaymentService {

  private final PaymentRepository paymentRepository;
  private final ListingRepository listingRepository;
  private final CacheManager cacheManager;

  @Value("${stripe.webhook-secret}")
  String webhookSecret;

  @Value("${stripe.secret-key}")
  String secretKey;

  @PostConstruct
  private void init() {
    Stripe.apiKey = secretKey;
  }

  @Override
  @SneakyThrows
  public String createIntent(ListingDTO listing) {
    PaymentIntentCreateParams createParams =
        new PaymentIntentCreateParams.Builder()
            .setCurrency("eur")
            .putMetadata("listingId", listing.id().toString())
            .setAmount(toCents(listing.category().price()))
            .build();
    PaymentIntent paymentIntent = PaymentIntent.create(createParams);

    return paymentIntent.getClientSecret();
  }

  private long toCents(BigDecimal price) {
    return price.movePointRight(2).longValueExact();
  }

  @Override
  @Transactional
  public void handleWebhook(String payload, String stripeSignature) {
    if (stripeSignature == null) {
      log.info("Webhook error stripeSignature == null");
      return;
    }

    if (webhookSecret == null) {
      log.info("Webhook error webhookSecret == null");
      return;
    }

    Event event;
    try {
      event = Webhook.constructEvent(payload, stripeSignature, webhookSecret);
    } catch (SignatureVerificationException e) {
      log.info("Webhook error while validating signature");
      throw new StripeWebhookException();
    }

    EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
    StripeObject stripeObject;
    if (dataObjectDeserializer.getObject().isPresent()) {
      stripeObject = dataObjectDeserializer.getObject().get();
    } else {
      log.info("Failed to deserialize StripeObject");
      throw new StripeWebhookException();
    }

    switch (event.getType()) {
      case "payment_intent.succeeded" -> {
        PaymentIntent paymentIntent = (PaymentIntent) stripeObject;
        log.info("Payment for {} succeeded", paymentIntent.getAmount());
        handlePaymentIntentSucceeded(paymentIntent);
      }
      default -> log.info("Unhandled event type: {}", event.getType());
    }
  }

  private void handlePaymentIntentSucceeded(PaymentIntent paymentIntent) {
    String paymentId = paymentIntent.getId();
    int listingId = Integer.parseInt(paymentIntent.getMetadata().get("listingId"));
    var listing = listingRepository.findById(listingId).orElseThrow(ListingNotFoundException::new);

    var payment = new Payment();
    payment.setPaymentId(paymentId);
    payment.setPrice(BigDecimal.valueOf(paymentIntent.getAmount()).movePointLeft(2));
    payment.setListing(listing);

    listing.setLive(true);
    listing.setCreatedAt(LocalDateTime.now());

    paymentRepository.save(payment);
    listingRepository.save(listing);

    cacheManager.getCache(CATEGORY_LISTING).clear();
  }
}
