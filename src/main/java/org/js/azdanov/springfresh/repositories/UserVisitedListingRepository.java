package org.js.azdanov.springfresh.repositories;

import java.util.Optional;
import org.js.azdanov.springfresh.models.UserVisitedListing;
import org.js.azdanov.springfresh.models.UserVisitedListingId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface UserVisitedListingRepository
    extends CrudRepository<UserVisitedListing, UserVisitedListingId> {

  @Query(
      "select l from UserVisitedListing l"
          + " where l.user.email = :email and l.listing.id = :listingId")
  Optional<UserVisitedListing> findByUserEmailAndListingId(String email, Integer listingId);

  @Query("select sum(l.visited) from UserVisitedListing l where l.listing.id = :listingId")
  int sumAllByListingId(Integer listingId);

  @Query(
      value =
          "select l from UserVisitedListing l"
              + " join fetch l.user lu join fetch l.listing ll"
              + " join fetch ll.category join fetch ll.area"
              + " where lu.email = :email and ll.live = true",
      countQuery =
          "select count(l) from UserVisitedListing l"
              + " left join l.user lu left join l.listing ll"
              + " where lu.email = :email and ll.live = true")
  Page<UserVisitedListing> findVisitedListings(String email, Pageable pageable);
}
