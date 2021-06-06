package org.js.azdanov.springfresh.controllers;

import lombok.RequiredArgsConstructor;
import org.js.azdanov.springfresh.dtos.AreaDTO;
import org.js.azdanov.springfresh.dtos.CategoryDTO;
import org.js.azdanov.springfresh.dtos.CategoryTreeDTO;
import org.js.azdanov.springfresh.dtos.ListingDTO;
import org.js.azdanov.springfresh.services.AreaService;
import org.js.azdanov.springfresh.services.CategoryService;
import org.js.azdanov.springfresh.services.ListingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/{areaSlug}/categories/{categorySlug}")
@RequiredArgsConstructor
public class ListingController {
  public final AreaService areaService;
  public final ListingService listingService;
  private final CategoryService categoryService;

  @GetMapping("/listings")
  public String index(
      @PathVariable String areaSlug,
      @PathVariable String categorySlug,
      Model model,
      @SortDefault.SortDefaults({
            @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC),
          })
          Pageable pageable) {
    AreaDTO areaDTO = areaService.findBySlug(areaSlug);
    CategoryDTO categoryDTO = categoryService.findBySlug(categorySlug);

    CategoryTreeDTO categoryTreeDTO = categoryService.getParent(categoryDTO);
    Page<ListingDTO> listings =
        listingService.findByAreaAndCategory(areaDTO, categoryDTO, pageable);

    model.addAttribute("category", categoryTreeDTO);
    model.addAttribute("listings", listings);

    return "listings/index";
  }

  @GetMapping("/listings/{listingId}")
  public String show(
      @PathVariable String areaSlug,
      @PathVariable String categorySlug,
      @PathVariable Integer listingId,
      Model model,
      @AuthenticationPrincipal UserDetails userDetails) {
    // TODO: Update currentArea in session to areaSlug

    ListingDTO listing = listingService.findById(listingId);

    if (!listing.live()) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "Listing %d is not live".formatted(listingId));
    }

    boolean hasUserFavorited =
        listingService.hasUserFavorited(listingId, userDetails.getUsername());
    model.addAttribute("listing", listing);
    model.addAttribute("hasUserFavorited", hasUserFavorited);

    return "listings/show";
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/listings/{listingId}/favorites")
  public String storeFavorite(
      @PathVariable String areaSlug,
      @PathVariable String categorySlug,
      @PathVariable Integer listingId,
      @AuthenticationPrincipal UserDetails userDetails) {

    listingService.storeFavoriteListing(listingId, userDetails.getUsername());

    return "redirect:/%s/categories/%s/listings/%d".formatted(areaSlug, categorySlug, listingId);
  }

  @PreAuthorize("isAuthenticated()")
  @DeleteMapping("/listings/{listingId}/favorites")
  public String deleteFavorite(
      @PathVariable String areaSlug,
      @PathVariable String categorySlug,
      @PathVariable Integer listingId,
      @AuthenticationPrincipal UserDetails userDetails) {

    listingService.deleteFavoriteListing(listingId, userDetails.getUsername());

    return "redirect:/%s/categories/%s/listings/%d".formatted(areaSlug, categorySlug, listingId);
  }
}