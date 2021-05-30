package org.js.azdanov.springfresh.models.listeners;

import com.github.slugify.Slugify;
import javax.persistence.PrePersist;
import org.jetbrains.annotations.NotNull;
import org.js.azdanov.springfresh.models.Area;
import org.js.azdanov.springfresh.repositories.AreaRepository;
import org.js.azdanov.springfresh.utils.BeanUtil;

public class AreaListener {
  @PrePersist
  public void setSlug(Area area) {
    String prefix = getPrefix(area);
    String slug = getSlug(area, prefix);
    area.setSlug(slug);
  }

  private String getPrefix(Area area) {
    var areaRepository = BeanUtil.getBean(AreaRepository.class);
    return area.getParentId() == null
        ? ""
        : areaRepository
            .findById(area.getParentId())
            .map(parent -> parent.getName() + " ")
            .orElse("");
  }

  private String getSlug(Area area, String prefix) {
    var slugify = BeanUtil.getBean(Slugify.class);
    return slugify.slugify(prefix + area.getName());
  }
}
