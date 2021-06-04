package org.js.azdanov.springfresh.repositories;

import java.util.List;
import org.js.azdanov.springfresh.models.Listing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ListingRepository extends CrudRepository<Listing, Integer> {
  @Query(
      value =
          "select l from Listing l"
              + " left join fetch l.user left join fetch l.area"
              + " where l.area.id in :areaIds and l.category.id = :categoryId and l.live = true",
      countQuery =
          "select count(l) from Listing l"
              + " left join l.user left join l.area"
              + " where l.area.id in :areaIds and l.category.id = :categoryId and l.live = true")
  Page<Listing> findAllActiveFor(List<Integer> areaIds, Integer categoryId, Pageable pageable);
}
