package org.js.azdanov.springfresh.services;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.js.azdanov.springfresh.controllers.requests.CreateListingForm;
import org.js.azdanov.springfresh.dtos.AreaDTO;
import org.js.azdanov.springfresh.dtos.CategoryDTO;
import org.js.azdanov.springfresh.dtos.FavoriteListingDTO;
import org.js.azdanov.springfresh.dtos.ListingDTO;
import org.js.azdanov.springfresh.dtos.UserDTO;
import org.js.azdanov.springfresh.dtos.VisitedListingDTO;
import org.js.azdanov.springfresh.exceptions.AreaNotFoundException;
import org.js.azdanov.springfresh.exceptions.CategoryNotFoundException;
import org.js.azdanov.springfresh.exceptions.ListingNotFoundException;
import org.js.azdanov.springfresh.exceptions.UserNotFoundException;
import org.js.azdanov.springfresh.models.Area;
import org.js.azdanov.springfresh.models.Category;
import org.js.azdanov.springfresh.models.Listing;
import org.js.azdanov.springfresh.models.User;
import org.js.azdanov.springfresh.models.UserFavoriteListing;
import org.js.azdanov.springfresh.models.UserVisitedListing;
import org.js.azdanov.springfresh.repositories.AreaRepository;
import org.js.azdanov.springfresh.repositories.CategoryRepository;
import org.js.azdanov.springfresh.repositories.ListingRepository;
import org.js.azdanov.springfresh.repositories.UserFavoriteListingRepository;
import org.js.azdanov.springfresh.repositories.UserRepository;
import org.js.azdanov.springfresh.repositories.UserVisitedListingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListingServiceImpl implements ListingService {
  public final UserRepository userRepository;
  public final AreaRepository areaRepository;
  public final CategoryRepository categoryRepository;
  public final ListingRepository listingRepository;
  public final UserFavoriteListingRepository userFavoriteListingRepository;
  public final UserVisitedListingRepository userVisitedListingRepository;

  @Override
  public Page<ListingDTO> getByAreaAndCategory(
      AreaDTO areaDTO, CategoryDTO categoryDTO, Pageable pageable) {
    List<Integer> areaIds = areaRepository.findInclusiveChildrenIds(areaDTO.slug());
    Page<Listing> listings =
        listingRepository.findAllActiveFor(areaIds, categoryDTO.id(), pageable);

    return getListingDTOPage(listings);
  }

  @Override
  public ListingDTO getById(Integer listingId) {
    return listingRepository
        .findByIdWithAreaAndCategoryAndUser(listingId)
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
    return user.hasFavoritedListing(listing);
  }

  @Override
  @Transactional
  public void deleteFavoriteListing(Integer listingId, String email) {
    Listing listing =
        listingRepository.findById(listingId).orElseThrow(ListingNotFoundException::new);
    User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    user.removeFavoriteListing(listing);
  }

  @Override
  public Page<FavoriteListingDTO> getFavoriteListings(String email, Pageable pageable) {
    Page<UserFavoriteListing> listings =
        userFavoriteListingRepository.findFavoriteListings(email, pageable);

    List<FavoriteListingDTO> favoriteListingDTOS =
        listings.stream()
            .map(
                (UserFavoriteListing userFavoriteListing) ->
                    new FavoriteListingDTO(
                        getListingDTO(userFavoriteListing.getListing()),
                        userFavoriteListing.getCreatedAt()))
            .toList();

    return new PageImpl<>(favoriteListingDTOS, listings.getPageable(), listings.getTotalElements());
  }

  @Override
  @Transactional
  public void incrementUserVisit(Integer listingId, String email) {
    Optional<UserVisitedListing> userVisitedListingOptional =
        userVisitedListingRepository.findByUserEmailAndListingId(email, listingId);

    if (userVisitedListingOptional.isPresent()) {
      userVisitedListingOptional.get().incrementVisited();
    } else {
      initUserVisitedListing(listingId, email);
    }
  }

  @Override
  public int sumAllUserVisits(Integer listingId) {
    return userVisitedListingRepository.sumAllByListingId(listingId);
  }

  @Override
  public Page<VisitedListingDTO> getVisitedListings(String email, Pageable pageable) {
    Page<UserVisitedListing> listings =
        userVisitedListingRepository.findVisitedListings(email, pageable);

    List<VisitedListingDTO> favoriteListingDTOS =
        listings.stream()
            .map(
                (UserVisitedListing userVisitedListing) ->
                    new VisitedListingDTO(
                        getListingDTO(userVisitedListing.getListing()),
                        userVisitedListing.getVisited(),
                        userVisitedListing.getCreatedAt(),
                        userVisitedListing.getUpdatedAt()))
            .toList();

    return new PageImpl<>(favoriteListingDTOS, listings.getPageable(), listings.getTotalElements());
  }

  //  @CacheEvict(value = {CATEGORY_LISTING}, key = "#result.area().slug()")
  @Override
  @Transactional
  public ListingDTO createListing(CreateListingForm listingForm) {
    var user =
        userRepository
            .findByEmail(listingForm.getUserEmail())
            .orElseThrow(UserNotFoundException::new);
    var area =
        areaRepository.findById(listingForm.getAreaId()).orElseThrow(AreaNotFoundException::new);
    var category =
        categoryRepository
            .findById(listingForm.getCategoryId())
            .orElseThrow(CategoryNotFoundException::new);

    Assert.state(user.isEnabled(), "createListing user(id=%d) must be enabled".formatted(user.getId()));
    Assert.state(area.isUsable(), "createListing area(id=%d) must be usable".formatted(area.getId()));
    Assert.state(category.isUsable(), "createListing category(id=%d) must be usable".formatted(category.getId()));

    var listing = new Listing();

    listing.setTitle(listingForm.getTitle());
    listing.setBody(listingForm.getBody());
    listing.setUser(user);
    listing.setCategory(category);
    listing.setArea(area);

    listing = listingRepository.save(listing);

    return new ListingDTO(
        listing.getId(),
        listing.getTitle(),
        listing.getBody(),
        listing.isLive(),
        getUserDTO(user),
        getAreaDTO(area),
        getCategoryDTO(category),
        listing.getCreatedAt());
  }

  private void initUserVisitedListing(Integer listingId, String email) {
    Listing listing =
        listingRepository.findById(listingId).orElseThrow(ListingNotFoundException::new);
    User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    listing.addVisitedByUser(user);
  }

  private Page<ListingDTO> getListingDTOPage(Page<Listing> listings) {
    List<ListingDTO> listingDTOS = listings.stream().map(this::getListingDTO).toList();
    return new PageImpl<>(listingDTOS, listings.getPageable(), listings.getTotalElements());
  }

  private ListingDTO getListingDTO(Listing listing) {
    return new ListingDTO(
        listing.getId(),
        listing.getTitle(),
        listing.getBody(),
        listing.isLive(),
        getUserDTO(listing.getUser()),
        getAreaDTO(listing.getArea()),
        getCategoryDTO(listing.getCategory()),
        listing.getCreatedAt());
  }

  private UserDTO getUserDTO(User user) {
    return new UserDTO(user.getId(), user.getName(), user.getEmail(), "", user.isEnabled());
  }

  private AreaDTO getAreaDTO(Area area) {
    return new AreaDTO(area);
  }

  private CategoryDTO getCategoryDTO(Category category) {
    return new CategoryDTO(category);
  }
}
