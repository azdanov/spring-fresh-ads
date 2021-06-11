package org.js.azdanov.springfresh.models;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class UserFavoriteListingId implements Serializable {
  static final long serialVersionUID = -1192120145739583721L;

  @Column(name = "user_id")
  private Integer userId;

  @Column(name = "listing_id")
  private Integer listingId;

  public UserFavoriteListingId(Integer userId, Integer listingId) {
    this.userId = userId;
    this.listingId = listingId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    UserFavoriteListingId that = (UserFavoriteListingId) o;
    return Objects.equals(userId, that.userId) && Objects.equals(listingId, that.listingId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, listingId);
  }
}
