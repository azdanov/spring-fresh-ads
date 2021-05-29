package org.js.azdanov.springfresh.loader;

import java.util.List;

public record AreaTreeDTO(String name, List<AreaTreeDTO> children) {}
