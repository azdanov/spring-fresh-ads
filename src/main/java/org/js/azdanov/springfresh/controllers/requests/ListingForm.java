package org.js.azdanov.springfresh.controllers.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.js.azdanov.springfresh.dtos.ListingDTO;

@Data
@NoArgsConstructor
public class ListingForm {
  private Integer id;

  @NotBlank
  @Size(max = 255)
  private String title;

  @NotBlank
  @Size(max = 2000)
  private String body;

  private boolean active;
  private boolean paid;
  private String userEmail;
  @Positive private int areaId;
  @Positive private int categoryId;
  private boolean payment;

  public ListingForm(ListingDTO listing) {
    id = listing.id();
    title = listing.title();
    body = listing.body();
    active = listing.active();
    userEmail = listing.user().email();
    areaId = listing.area().id();
    categoryId = listing.category().id();
    paid = listing.payment() != null;
  }
}
