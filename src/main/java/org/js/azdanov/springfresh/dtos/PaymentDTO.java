package org.js.azdanov.springfresh.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.js.azdanov.springfresh.models.Payment;

public record PaymentDTO(
    Integer id,
    String paymentId,
    BigDecimal price,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {
  public PaymentDTO(Payment payment) {
    this(
        payment.getId(),
        payment.getPaymentId(),
        payment.getPrice(),
        payment.getCreatedAt(),
        payment.getUpdatedAt());
  }
}
