package org.js.azdanov.springfresh.dtos;

import java.math.BigDecimal;
import org.js.azdanov.springfresh.models.Category;

public record CategoryDTO(Integer id, String name, String slug, BigDecimal price, boolean usable) {
  public CategoryDTO(Category category) {
    this(
        category.getId(),
        category.getName(),
        category.getSlug(),
        category.getPrice(),
        category.isUsable());
  }
}
