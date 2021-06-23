package org.js.azdanov.springfresh.dtos;

import java.util.List;

public record AreaTreeDTO(
    Integer id, String name, String slug, boolean usable, List<AreaTreeDTO> children) {}
