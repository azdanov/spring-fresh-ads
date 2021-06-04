package org.js.azdanov.springfresh.services;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.js.azdanov.springfresh.dtos.AreaDTO;
import org.js.azdanov.springfresh.dtos.CategoryDTO;
import org.js.azdanov.springfresh.dtos.ListingDTO;
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
public class ListingServiceImpl implements ListingService {
  public final AreaRepository areaRepository;
  public final CategoryRepository categoryRepository;
  public final ListingRepository listingRepository;

  @Override
  @Transactional(readOnly = true)
  public Page<ListingDTO> findByAreaAndCategory(
      AreaDTO areaDTO, CategoryDTO categoryDTO, Pageable pageable) {
    List<Integer> areaIds = areaRepository.findInclusiveChildrenIds(areaDTO.slug());
    Page<Listing> listings =
        listingRepository.findAllActiveFor(areaIds, categoryDTO.id(), pageable);

    List<ListingDTO> listingDTOS =
        listings.stream()
            .map(
                listing ->
                    new ListingDTO(
                        listing.getTitle(),
                        listing.getBody(),
                        listing.getUser().getName(),
                        listing.getArea().getName(),
                        listing.getCreatedAt()))
            .toList();

    return new PageImpl<>(listingDTOS, listings.getPageable(), listings.getTotalElements());
  }
}
