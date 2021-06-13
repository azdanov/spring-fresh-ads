package org.js.azdanov.springfresh.controllers;

import lombok.RequiredArgsConstructor;
import org.js.azdanov.springfresh.dtos.FavoriteListingDTO;
import org.js.azdanov.springfresh.dtos.VisitedListingDTO;
import org.js.azdanov.springfresh.services.ListingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ViewedListingController {
  public final ListingService listingService;

  @GetMapping("/listings/visited")
  public String index(
      @AuthenticationPrincipal UserDetails userDetails,
      Model model,
      @PageableDefault(sort = "updatedAt", direction = Sort.Direction.DESC) Pageable pageable) {
    Page<VisitedListingDTO> visitedListings =
        listingService.getVisitedListings(userDetails.getUsername(), pageable);
    model.addAttribute("visitedListings", visitedListings);
    return "user/listings/visited/index";
  }
}
