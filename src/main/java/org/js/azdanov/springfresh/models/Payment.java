package org.js.azdanov.springfresh.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Table(name = "payment")
@Entity
@Getter
@Setter
public class Payment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String paymentId;

  private BigDecimal price;

  @OneToOne(fetch = FetchType.LAZY)
  @MapsId
  private Listing listing;

  @CreationTimestamp private LocalDateTime createdAt;
  @UpdateTimestamp private LocalDateTime updatedAt;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Payment payment = (Payment) o;

    return Objects.equals(id, payment.id);
  }

  @Override
  public int hashCode() {
    return 1545849159;
  }

  @Override
  public String toString() {
    return "%s(id = %d, paymentId = %s, price = %s, createdAt = %s, updatedAt = %s)"
        .formatted(getClass().getSimpleName(), id, paymentId, price, createdAt, updatedAt);
  }
}
