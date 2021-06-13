package org.js.azdanov.springfresh.models;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserVisitedListingId implements Serializable {
  static final long serialVersionUID = -8332676910719193338L;

  @Column(name = "user_id")
  private Integer userId;

  @Column(name = "listing_id")
  private Integer listingId;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    UserVisitedListingId that = (UserVisitedListingId) o;
    return Objects.equals(userId, that.userId) && Objects.equals(listingId, that.listingId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, listingId);
  }
}
