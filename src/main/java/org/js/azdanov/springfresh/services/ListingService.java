package org.js.azdanov.springfresh.services;

import org.js.azdanov.springfresh.dtos.AreaDTO;
import org.js.azdanov.springfresh.dtos.CategoryDTO;
import org.js.azdanov.springfresh.dtos.ListingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListingService {
  Page<ListingDTO> findByAreaAndCategory(
      AreaDTO areaDTO, CategoryDTO categoryDTO, Pageable pageable);

  ListingDTO findById(Integer listingId);
}
