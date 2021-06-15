package org.js.azdanov.springfresh.controllers.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class ListingContactForm {
  @NotBlank
  @Size(max = 1000)
  private String message;
}
