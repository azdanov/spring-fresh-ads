package org.js.azdanov.springfresh.services;

import org.js.azdanov.springfresh.controllers.requests.ListingForm;
import org.js.azdanov.springfresh.dtos.AreaDTO;
import org.js.azdanov.springfresh.dtos.CategoryDTO;
import org.js.azdanov.springfresh.dtos.FavoriteListingDTO;
import org.js.azdanov.springfresh.dtos.ListingDTO;
import org.js.azdanov.springfresh.dtos.VisitedListingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListingService {
  Page<ListingDTO> getByAreaAndCategory(
      AreaDTO areaDTO, CategoryDTO categoryDTO, Pageable pageable);

  Page<ListingDTO> getByUserEmail(String email, Pageable pageable);

  ListingDTO getById(Integer listingId);

  boolean belongsTo(Integer listingId, String email);

  void storeFavoriteListing(Integer listingId, String email);

  boolean hasUserFavorited(Integer listingId, String email);

  void deleteFavoriteListing(Integer listingId, String email);

  Page<FavoriteListingDTO> getFavoriteListings(String email, Pageable pageable);

  void incrementUserVisit(Integer listingId, String email);

  int sumAllUserVisits(Integer listingId);

  Page<VisitedListingDTO> getVisitedListings(String email, Pageable pageable);

  ListingDTO createListing(ListingForm listingForm);

  ListingDTO updateListing(ListingForm listingForm);

  void handleFreeListing(Integer listingId);

  void delete(Integer listingId);
}
