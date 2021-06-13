package org.js.azdanov.springfresh.models;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Table(name = "users")
@Entity
@Getter
@Setter
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotBlank
  @Size(max = 120)
  @Column(nullable = false)
  private String name;

  @NotBlank
  @Size(max = 120)
  @Email
  @Column(nullable = false, unique = true)
  private String email;

  @NotBlank
  @Size(max = 200)
  @Column(nullable = false)
  private String password;

  private boolean enabled;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Listing> listings = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<UserFavoriteListing> favoriteListings = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<UserVisitedListing> visitedListings = new ArrayList<>();

  @CreationTimestamp private LocalDateTime createdAt;

  @UpdateTimestamp private LocalDateTime updatedAt;

  @ManyToMany
  @JoinTable(
      name = "users_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles;

  public User() {}

  public User(String name, String email, String password) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.enabled = false;
  }

  public void addListing(Listing listing) {
    listings.add(listing);
    listing.setUser(this);
  }

  public void removeListing(Listing listing) {
    listings.remove(listing);
    listing.setUser(null);
  }

  public void addFavoriteListing(Listing listing) {
    UserFavoriteListing userFavoriteListing = new UserFavoriteListing(this, listing);
    favoriteListings.add(userFavoriteListing);
    listing.getFavoriteByUsers().add(userFavoriteListing);
  }

  public boolean hasFavoritedListing(Listing listing) {
    for (UserFavoriteListing userFavoriteListing : favoriteListings) {
      if (userFavoriteListing.getUser().equals(this)
          && userFavoriteListing.getListing().equals(listing)) {
        return true;
      }
    }
    return false;
  }

  public void removeFavoriteListing(Listing listing) {
    for (Iterator<UserFavoriteListing> iterator = favoriteListings.iterator();
        iterator.hasNext(); ) {
      UserFavoriteListing userFavoriteListing = iterator.next();

      if (userFavoriteListing.getUser().equals(this)
          && userFavoriteListing.getListing().equals(listing)) {
        iterator.remove();
        userFavoriteListing.getListing().getFavoriteByUsers().remove(userFavoriteListing);
        userFavoriteListing.setUser(null);
        userFavoriteListing.setListing(null);
      }
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    User user = (User) o;

    return id != null && id.equals(user.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  @Override
  public String toString() {
    return MessageFormat.format(
        "{0}(id = {1}, name = {2}, email = {3}, enabled = {4}, createdAt = {5}, updatedAt = {6})",
        getClass().getSimpleName(), id, name, email, enabled, createdAt, updatedAt);
  }
}
