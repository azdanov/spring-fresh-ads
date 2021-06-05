package org.js.azdanov.springfresh.models;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Table(name = "verification_tokens")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class VerificationToken {
  private static final Duration EXPIRATION = Duration.of(24, ChronoUnit.HOURS);

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  private String token;

  @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
  private User user;

  @Column(nullable = false)
  private LocalDateTime expiryDate;

  @CreationTimestamp private LocalDateTime createdAt;

  @UpdateTimestamp private LocalDateTime updatedAt;

  public VerificationToken(String token, User user) {
    this.token = token;
    this.user = user;
    this.expiryDate = calculateExpiryDate();
  }

  private LocalDateTime calculateExpiryDate() {
    return LocalDateTime.now().plus(VerificationToken.EXPIRATION);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    VerificationToken that = (VerificationToken) o;

    return id != null && id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return 350374624;
  }

  @Override
  public String toString() {
    return MessageFormat.format(
        "{0}(id = {1}, token = {2}, expiryDate = {3}, createdAt = {4}, updatedAt = {5})",
        getClass().getSimpleName(), id, token, expiryDate, createdAt, updatedAt);
  }
}
