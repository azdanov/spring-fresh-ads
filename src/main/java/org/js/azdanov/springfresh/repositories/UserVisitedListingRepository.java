package org.js.azdanov.springfresh.repositories;

import java.util.Optional;
import org.js.azdanov.springfresh.models.UserVisitedListing;
import org.js.azdanov.springfresh.models.UserVisitedListingId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface UserVisitedListingRepository
    extends CrudRepository<UserVisitedListing, UserVisitedListingId> {

  @Query(
      "select uvl from UserVisitedListing uvl"
          + " where uvl.user.email = :email and uvl.listing.id = :listingId")
  Optional<UserVisitedListing> findByUserEmailAndListingId(String email, Integer listingId);

  @Query("select sum(uvl.visited) from UserVisitedListing uvl where uvl.listing.id = :listingId")
  int sumAllByListingId(Integer listingId);
}
