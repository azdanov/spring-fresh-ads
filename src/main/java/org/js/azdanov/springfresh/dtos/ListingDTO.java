package org.js.azdanov.springfresh.dtos;

import java.time.LocalDateTime;
import org.js.azdanov.springfresh.models.Area;
import org.js.azdanov.springfresh.models.Category;
import org.js.azdanov.springfresh.models.Listing;
import org.js.azdanov.springfresh.models.User;

public record ListingDTO(
    Integer id,
    String title,
    String body,
    boolean live,
    UserDTO user,
    AreaDTO area,
    CategoryDTO category,
    LocalDateTime createdAt) {
  public ListingDTO(Listing listing) {
    this(listing.getId(),
        listing.getTitle(),
        listing.getBody(),
        listing.isLive(),
        getUserDTO(listing.getUser()),
        getAreaDTO(listing.getArea()),
        getCategoryDTO(listing.getCategory()),
        listing.getCreatedAt());
  }

  private static UserDTO getUserDTO(User user) {
    return new UserDTO(user.getId(), user.getName(), user.getEmail(), "", user.isEnabled());
  }

  private static AreaDTO getAreaDTO(Area area) {
    return new AreaDTO(area);
  }

  private static CategoryDTO getCategoryDTO(Category category) {
    return new CategoryDTO(category);
  }
}
