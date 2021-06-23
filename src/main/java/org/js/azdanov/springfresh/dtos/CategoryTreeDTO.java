package org.js.azdanov.springfresh.dtos;

import java.math.BigDecimal;
import java.util.List;

public record CategoryTreeDTO(
    Integer id,
    String name,
    String slug,
    BigDecimal price,
    boolean usable,
    List<CategoryTreeDTO> children) {}
