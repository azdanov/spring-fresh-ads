package org.js.azdanov.springfresh.controllers;

import lombok.RequiredArgsConstructor;
import org.js.azdanov.springfresh.dtos.AreaDTO;
import org.js.azdanov.springfresh.dtos.CategoryDTO;
import org.js.azdanov.springfresh.dtos.CategoryTreeDTO;
import org.js.azdanov.springfresh.dtos.ListingDTO;
import org.js.azdanov.springfresh.services.CategoryService;
import org.js.azdanov.springfresh.services.ListingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/{areaSlug}/categories/{categorySlug}")
@RequiredArgsConstructor
public class ListingController {
  public final ListingService listingService;
  private final CategoryService categoryService;

  @GetMapping("/listings")
  public String index(
      AreaDTO areaDTO,
      CategoryDTO categoryDTO,
      Model model,
      @SortDefault.SortDefaults({
            @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC),
          })
          Pageable pageable) {
    CategoryTreeDTO categoryTreeDTO = categoryService.getParent(categoryDTO);
    Page<ListingDTO> listings =
        listingService.findByAreaAndCategory(areaDTO, categoryDTO, pageable);

    model.addAttribute("category", categoryTreeDTO);
    model.addAttribute("listings", listings);

    return "listings/index";
  }
}
