package org.js.azdanov.springfresh.repositories;

import org.js.azdanov.springfresh.models.UserFavoriteListing;
import org.js.azdanov.springfresh.models.UserFavoriteListingId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserFavoriteListingRepository
    extends PagingAndSortingRepository<UserFavoriteListing, UserFavoriteListingId> {
  @Query(
      value =
          "select l from UserFavoriteListing l"
              + " join fetch l.user lu join fetch l.listing ll"
              + " join fetch ll.category join fetch ll.area"
              + " where lu.email = :email and ll.active = true",
      countQuery =
          "select count(l) from UserFavoriteListing l"
              + " left join l.user lu left join l.listing ll"
              + " where lu.email = :email and ll.active = true")
  Page<UserFavoriteListing> findFavoriteListings(String email, Pageable pageable);
}
