package org.js.azdanov.springfresh.repositories;

import java.util.List;
import java.util.Optional;
import org.js.azdanov.springfresh.models.Listing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ListingRepository extends PagingAndSortingRepository<Listing, Integer> {
  @Query(
      value =
          "select l from Listing l"
              + " left join fetch l.user left join fetch l.area"
              + " where l.area.id in :areaIds and l.category.id = :categoryId and l.active = true",
      countQuery =
          "select count(l) from Listing l"
              + " left join l.user left join l.area"
              + " where l.area.id in :areaIds and l.category.id = :categoryId and l.active = true")
  Page<Listing> findAllActiveFor(List<Integer> areaIds, Integer categoryId, Pageable pageable);

  @Query(
      value =
          "select l from Listing l"
              + " left join fetch l.user left join fetch l.area"
              + " where l.user.email = :email",
      countQuery =
          "select count(l) from Listing l"
              + " left join l.user left join l.area"
              + " where l.user.email = :email")
  Page<Listing> findAllFor(String email, Pageable pageable);

  @Query(
      "select l from Listing l"
          + " join fetch l.area join fetch l.category join fetch l.user"
          + " where l.id = :listingId")
  Optional<Listing> findByIdWithAreaAndCategoryAndUser(Integer listingId);

  @Query(
      "select case when count(l) > 0 then true else false end from Listing l"
          + " left join l.user"
          + " where l.id = :listingId and l.user.email = :email")
  boolean listingBelongsTo(Integer listingId, String email);

  @Query(
      "select l from Listing l"
          + " where search_function(title, :query) = true or search_function(body, :query) = true")
  Page<Listing> findForQuery(String query, Pageable pageable);
}
