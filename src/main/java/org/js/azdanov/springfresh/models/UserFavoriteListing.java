package org.js.azdanov.springfresh.models;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "user_favorite_listing")
@Getter
@Setter
@NoArgsConstructor
public class UserFavoriteListing {
  @EmbeddedId private UserFavoriteListingId id;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("userId")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("listingId")
  private Listing listing;

  @CreationTimestamp private LocalDateTime createdAt;

  public UserFavoriteListing(User user, Listing listing) {
    this.user = user;
    this.listing = listing;
    this.id = new UserFavoriteListingId(user.getId(), listing.getId());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    UserFavoriteListing that = (UserFavoriteListing) o;
    return Objects.equals(user, that.user) && Objects.equals(listing, that.listing);
  }

  @Override
  public int hashCode() {
    return Objects.hash(user, listing);
  }
}
