package org.js.azdanov.springfresh.controllers.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateListingForm {
  @NotBlank
  @Size(max = 255)
  private String title;

  @NotBlank
  @Size(max = 2000)
  private String body;

  private boolean live;
  private String userEmail;
  @Positive private int areaId;
  @Positive private int categoryId;
}
