package org.js.azdanov.springfresh.controllers;

import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.js.azdanov.springfresh.dtos.ListingDTO;
import org.js.azdanov.springfresh.services.AreaService;
import org.js.azdanov.springfresh.services.ListingService;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class FavoriteListingController {
  public final AreaService areaService;
  public final ListingService listingService;
  public final MessageSource messageSource;

  @GetMapping("/listings/favorites")
  public String index(
      @AuthenticationPrincipal UserDetails userDetails, Model model, Pageable pageable) {
    // TODO: order favorites by 'favoritedAt' date
    Page<ListingDTO> listings =
        listingService.getFavoriteListings(userDetails.getUsername(), pageable);
    model.addAttribute("listings", listings);
    return "user/listings/favorites/index";
  }

  @PostMapping("/{areaSlug}/categories/{categorySlug}/listings/{listingId}/favorites")
  public String store(
      @PathVariable String areaSlug,
      @PathVariable String categorySlug,
      @PathVariable Integer listingId,
      @AuthenticationPrincipal UserDetails userDetails,
      RedirectAttributes redirectAttributes,
      Locale locale) {
    listingService.storeFavoriteListing(listingId, userDetails.getUsername());

    redirectAttributes.addFlashAttribute(
        "toastDefault", messageSource.getMessage("toast.listing.favorited", null, locale));

    return "redirect:/%s/categories/%s/listings/%d".formatted(areaSlug, categorySlug, listingId);
  }

  @DeleteMapping({
    "/listings/{listingId}/favorites",
    "/{areaSlug}/categories/{categorySlug}/listings/{listingId}/favorites"
  })
  public String destroy(
      @PathVariable(required = false) String areaSlug,
      @PathVariable(required = false) String categorySlug,
      @PathVariable Integer listingId,
      @AuthenticationPrincipal UserDetails userDetails,
      RedirectAttributes redirectAttributes,
      Locale locale) {
    listingService.deleteFavoriteListing(listingId, userDetails.getUsername());

    redirectAttributes.addFlashAttribute(
        "toastDefault", messageSource.getMessage("toast.listing.unfavorited", null, locale));

    if (areaSlug == null || categorySlug == null) {
      return "redirect:/listings/favorites";
    }
    return "redirect:/%s/categories/%s/listings/%d".formatted(areaSlug, categorySlug, listingId);
  }
}
