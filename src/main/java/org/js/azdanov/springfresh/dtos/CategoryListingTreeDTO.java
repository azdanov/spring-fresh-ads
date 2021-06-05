package org.js.azdanov.springfresh.dtos;

import java.math.BigDecimal;
import java.util.List;

public record CategoryListingTreeDTO(
    String name, String slug, BigDecimal price, long listings, List<CategoryListingTreeDTO> children) {}
