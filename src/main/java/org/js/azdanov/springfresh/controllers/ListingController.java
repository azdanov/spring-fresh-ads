package org.js.azdanov.springfresh.controllers;

import java.util.Locale;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.js.azdanov.springfresh.controllers.requests.ListingContactForm;
import org.js.azdanov.springfresh.dtos.AreaDTO;
import org.js.azdanov.springfresh.dtos.CategoryDTO;
import org.js.azdanov.springfresh.dtos.CategoryTreeDTO;
import org.js.azdanov.springfresh.dtos.ListingDTO;
import org.js.azdanov.springfresh.services.AreaService;
import org.js.azdanov.springfresh.services.CategoryService;
import org.js.azdanov.springfresh.services.ContactService;
import org.js.azdanov.springfresh.services.ListingService;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequiredArgsConstructor
public class ListingController {
  public final AreaService areaService;
  public final ListingService listingService;
  private final CategoryService categoryService;
  private final ContactService contactService;
  private final MessageSource messageSource;

  @GetMapping("/{areaSlug}/categories/{categorySlug}/listings")
  public String index(
      @PathVariable String areaSlug,
      @PathVariable String categorySlug,
      Model model,
      @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
    AreaDTO areaDTO = areaService.findBySlug(areaSlug);
    CategoryDTO categoryDTO = categoryService.findBySlug(categorySlug);

    CategoryTreeDTO categoryTreeDTO = categoryService.getParent(categoryDTO);
    Page<ListingDTO> listings = listingService.getByAreaAndCategory(areaDTO, categoryDTO, pageable);

    model.addAttribute("category", categoryTreeDTO);
    model.addAttribute("listings", listings);

    return "listings/index";
  }

  @GetMapping("/{areaSlug}/categories/{categorySlug}/listings/{listingId}")
  public String show(
      @PathVariable String areaSlug,
      @PathVariable String categorySlug,
      @PathVariable Integer listingId,
      Model model,
      @AuthenticationPrincipal UserDetails userDetails) {
    ListingDTO listing = listingService.getById(listingId);

    if (!listing.live()) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "Listing %d is not live".formatted(listingId));
    }

    if (userDetails != null) {
      boolean hasUserFavorited =
          listingService.hasUserFavorited(listingId, userDetails.getUsername());
      model.addAttribute("hasUserFavorited", hasUserFavorited);

      listingService.incrementUserVisit(listingId, userDetails.getUsername());
      int totalVisits = listingService.sumAllUserVisits(listingId);
      model.addAttribute("totalVisits", totalVisits);
    }

    model.addAttribute("listing", listing);

    if (!model.containsAttribute("contact")) {
      model.addAttribute("contact", new ListingContactForm());
    }

    return "listings/show";
  }

  @PostMapping("/{areaSlug}/categories/{categorySlug}/listings/{listingId}/contact")
  public String storeContact(
      @PathVariable String areaSlug,
      @PathVariable String categorySlug,
      @PathVariable Integer listingId,
      @Valid @ModelAttribute("contact") ListingContactForm formData,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes,
      @AuthenticationPrincipal UserDetails userDetails,
      Locale locale,
      UriComponentsBuilder uriComponentsBuilder) {

    if (bindingResult.hasErrors()) {
      redirectAttributes.addFlashAttribute(
          "org.springframework.validation.BindingResult.contact", bindingResult);
      redirectAttributes.addFlashAttribute("contact", formData);
      return "redirect:/%s/categories/%s/listings/%d".formatted(areaSlug, categorySlug, listingId);
    }

    var listingURI = getListingURI(uriComponentsBuilder, areaSlug, categorySlug, listingId);
    ListingDTO listing = listingService.getById(listingId);
    contactService.sendMessage(
        listing, formData.getMessage(), userDetails.getUsername(), listingURI);

    redirectAttributes.addFlashAttribute(
        "toastSuccess",
        messageSource.getMessage(
            "toast.contact.sent", new String[] {listing.user().name()}, locale));

    return "redirect:/%s/categories/%s/listings/%d".formatted(areaSlug, categorySlug, listingId);
  }

  private String getListingURI(
      UriComponentsBuilder uriComponentsBuilder,
      String areaSlug,
      String categorySlug,
      Integer listingId) {
    return uriComponentsBuilder
        .uriComponents(
            MvcUriComponentsBuilder.fromMethodName(
                    ListingController.class, "show", areaSlug, categorySlug, listingId, null, null)
                .build())
        .toUriString();
  }
}
