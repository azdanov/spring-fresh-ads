package org.js.azdanov.springfresh.models;

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
import org.hibernate.Hibernate;

@Entity
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

  public VerificationToken() {}

  public VerificationToken(final String token) {
    this.token = token;
    this.expiryDate = calculateExpiryDate();
  }

  public VerificationToken(String token, User user) {
    this.token = token;
    this.user = user;
    this.expiryDate = calculateExpiryDate();
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public LocalDateTime getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(LocalDateTime expiryDate) {
    this.expiryDate = expiryDate;
  }

  private LocalDateTime calculateExpiryDate() {
    return LocalDateTime.now().plus(VerificationToken.EXPIRATION);
  }

  public void updateToken(String token) {
    this.token = token;
    this.expiryDate = calculateExpiryDate();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    VerificationToken that = (VerificationToken) o;

    return id != null && id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return 350374624;
  }

  @Override
  public String toString() {
    return "%s(id = %d, token = %s, expiryDate = %s)"
        .formatted(getClass().getSimpleName(), id, token, expiryDate);
  }
}
