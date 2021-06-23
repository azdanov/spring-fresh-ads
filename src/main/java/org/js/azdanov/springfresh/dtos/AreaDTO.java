package org.js.azdanov.springfresh.dtos;

import org.js.azdanov.springfresh.models.Area;

public record AreaDTO(Integer id, String name, String slug, boolean usable) {
  public AreaDTO(Area area) {
    this(area.getId(), area.getName(), area.getSlug(), area.isUsable());
  }
}
