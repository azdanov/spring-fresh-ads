package org.js.azdanov.springfresh.dtos;

import java.math.BigDecimal;
import java.util.List;

public record CategoryTreeDTO(
    String name, String slug, BigDecimal price, List<CategoryTreeDTO> children) {}
