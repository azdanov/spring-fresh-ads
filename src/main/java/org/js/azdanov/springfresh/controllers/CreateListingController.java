package org.js.azdanov.springfresh.controllers;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.js.azdanov.springfresh.controllers.requests.CreateListingForm;
import org.js.azdanov.springfresh.dtos.ListingDTO;
import org.js.azdanov.springfresh.services.AreaService;
import org.js.azdanov.springfresh.services.CategoryService;
import org.js.azdanov.springfresh.services.ListingService;
import org.js.azdanov.springfresh.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequiredArgsConstructor
public class CreateListingController {
  public final UserService userService;
  public final AreaService areaService;
  public final ListingService listingService;
  private final CategoryService categoryService;

  @GetMapping("/listings/create")
  public String create(Model model) {

    var areas = areaService.getAllAreasTree();
    var categories = categoryService.getAllCategories();

    model.addAttribute("areas", areas);
    model.addAttribute("categories", categories);

    if (!model.containsAttribute("listing")) {
      model.addAttribute("listing", new CreateListingForm());
    }

    return "listings/create";
  }

  @PostMapping("/listings")
  public String store(
      @Valid @ModelAttribute("listing") CreateListingForm formData,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes,
      @AuthenticationPrincipal UserDetails userDetails,
      UriComponentsBuilder uriComponentsBuilder) {

    redirectAttributes.addFlashAttribute("listing", formData);

    if (bindingResult.hasErrors()) {
      redirectAttributes.addFlashAttribute(
          "org.springframework.validation.BindingResult.listing", bindingResult);
      return "redirect:listings/create";
    }

    formData.setUserEmail(userDetails.getUsername());
    var listing = listingService.createListing(formData);
    //    var listingURI = getListingURI(uriComponentsBuilder, listing);

    return "redirect:listings/create";
  }

  private String getListingURI(UriComponentsBuilder uriComponentsBuilder, ListingDTO listing) {
    return uriComponentsBuilder
        .uriComponents(
            MvcUriComponentsBuilder.fromMethodName(
                    ListingController.class,
                    "show",
                    listing.area().slug(),
                    listing.category().slug(),
                    listing.id(),
                    null,
                    null)
                .build())
        .toUriString();
  }
}
