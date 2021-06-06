package org.js.azdanov.springfresh.services;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.js.azdanov.springfresh.dtos.AreaDTO;
import org.js.azdanov.springfresh.dtos.CategoryDTO;
import org.js.azdanov.springfresh.dtos.ListingDTO;
import org.js.azdanov.springfresh.exceptions.ListingNotFoundException;
import org.js.azdanov.springfresh.exceptions.UserNotFoundException;
import org.js.azdanov.springfresh.models.Listing;
import org.js.azdanov.springfresh.models.User;
import org.js.azdanov.springfresh.repositories.AreaRepository;
import org.js.azdanov.springfresh.repositories.CategoryRepository;
import org.js.azdanov.springfresh.repositories.ListingRepository;
import org.js.azdanov.springfresh.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListingServiceImpl implements ListingService {
  public final UserRepository userRepository;
  public final AreaRepository areaRepository;
  public final CategoryRepository categoryRepository;
  public final ListingRepository listingRepository;

  @Override
  public Page<ListingDTO> findByAreaAndCategory(
      AreaDTO areaDTO, CategoryDTO categoryDTO, Pageable pageable) {
    List<Integer> areaIds = areaRepository.findInclusiveChildrenIds(areaDTO.slug());
    Page<Listing> listings =
        listingRepository.findAllActiveFor(areaIds, categoryDTO.id(), pageable);

    List<ListingDTO> listingDTOS = listings.stream().map(this::getListingDTO).toList();

    return new PageImpl<>(listingDTOS, listings.getPageable(), listings.getTotalElements());
  }

  @Override
  public ListingDTO findById(Integer listingId) {
    return listingRepository
        .findById(listingId)
        .map(this::getListingDTO)
        .orElseThrow(ListingNotFoundException::new);
  }

  @Override
  @Transactional
  public void storeFavoriteListing(Integer listingId, String email) {
    Listing listing =
        listingRepository.findById(listingId).orElseThrow(ListingNotFoundException::new);
    User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    user.addFavoriteListing(listing);
  }

  @Override
  public boolean hasUserFavorited(Integer listingId, String email) {
    Listing listing =
        listingRepository.findById(listingId).orElseThrow(ListingNotFoundException::new);
    User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    return user.getFavoriteListings().contains(listing);
  }

  @Override
  @Transactional
  public void deleteFavoriteListing(Integer listingId, String email) {
    Listing listing =
        listingRepository.findById(listingId).orElseThrow(ListingNotFoundException::new);
    User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    user.removeFavoriteListing(listing);
  }

  private ListingDTO getListingDTO(Listing listing) {
    return new ListingDTO(
        listing.getId(),
        listing.getTitle(),
        listing.getBody(),
        listing.isLive(),
        listing.getUser().getName(),
        listing.getArea().getName(),
        listing.getCreatedAt());
  }
}
