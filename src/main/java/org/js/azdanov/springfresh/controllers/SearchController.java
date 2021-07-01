package org.js.azdanov.springfresh.controllers;

import lombok.RequiredArgsConstructor;
import org.js.azdanov.springfresh.dtos.ListingDTO;
import org.js.azdanov.springfresh.services.ListingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class SearchController {
  private final ListingService listingService;

  @GetMapping("/search")
  public String index(
      @RequestParam String query,
      Model model,
      @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
    Page<ListingDTO> listings = listingService.findByQuery(query, pageable);

    model.addAttribute("query", query);
    model.addAttribute("listings", listings);

    return "search/index";
  }
}
