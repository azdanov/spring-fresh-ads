package org.js.azdanov.springfresh.controllers;

import lombok.RequiredArgsConstructor;
import org.js.azdanov.springfresh.dtos.ListingDTO;
import org.js.azdanov.springfresh.services.AreaService;
import org.js.azdanov.springfresh.services.ListingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class FavoriteListingController {
  public final AreaService areaService;
  public final ListingService listingService;

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/listings/favorites")
  public String index(
      @AuthenticationPrincipal UserDetails userDetails, Model model, Pageable pageable) {
    // TODO: order favorites by 'favoritedAt' date
    Page<ListingDTO> listings =
        listingService.getFavoriteListings(userDetails.getUsername(), pageable);
    model.addAttribute("listings", listings);
    return "user/listings/favorites/index";
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/{areaSlug}/categories/{categorySlug}/listings/{listingId}/favorites")
  public String store(
      @PathVariable String areaSlug,
      @PathVariable String categorySlug,
      @PathVariable Integer listingId,
      @AuthenticationPrincipal UserDetails userDetails) {
    listingService.storeFavoriteListing(listingId, userDetails.getUsername());

    return "redirect:/%s/categories/%s/listings/%d".formatted(areaSlug, categorySlug, listingId);
  }

  @PreAuthorize("isAuthenticated()")
  @DeleteMapping({
    "/listings/{listingId}/favorites",
    "/{areaSlug}/categories/{categorySlug}/listings/{listingId}/favorites"
  })
  public String destroy(
      @PathVariable(required = false) String areaSlug,
      @PathVariable(required = false) String categorySlug,
      @PathVariable Integer listingId,
      @AuthenticationPrincipal UserDetails userDetails) {
    listingService.deleteFavoriteListing(listingId, userDetails.getUsername());

    if (areaSlug == null || categorySlug == null) {
      return "redirect:/listings/favorites";
    }
    return "redirect:/%s/categories/%s/listings/%d".formatted(areaSlug, categorySlug, listingId);
  }
}
