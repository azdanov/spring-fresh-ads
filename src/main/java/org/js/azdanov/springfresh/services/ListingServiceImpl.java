package org.js.azdanov.springfresh.services;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.js.azdanov.springfresh.dtos.AreaDTO;
import org.js.azdanov.springfresh.dtos.CategoryDTO;
import org.js.azdanov.springfresh.dtos.ListingDTO;
import org.js.azdanov.springfresh.exceptions.ListingNotFoundException;
import org.js.azdanov.springfresh.models.Listing;
import org.js.azdanov.springfresh.repositories.AreaRepository;
import org.js.azdanov.springfresh.repositories.CategoryRepository;
import org.js.azdanov.springfresh.repositories.ListingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListingServiceImpl implements ListingService {
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
