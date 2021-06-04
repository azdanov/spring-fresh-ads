package org.js.azdanov.springfresh.dtos;

import java.math.BigDecimal;

public record CategoryDTO(Integer id, String name, String slug, BigDecimal price) {}
