package org.js.azdanov.springfresh.dtos;

import java.util.List;

public record AreaTreeDTO(String name, List<AreaTreeDTO> children) {}
