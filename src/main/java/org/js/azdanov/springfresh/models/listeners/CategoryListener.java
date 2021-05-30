package org.js.azdanov.springfresh.models.listeners;

import com.github.slugify.Slugify;
import javax.persistence.PrePersist;
import org.js.azdanov.springfresh.models.Category;
import org.js.azdanov.springfresh.repositories.CategoryRepository;
import org.js.azdanov.springfresh.utils.BeanUtil;

public class CategoryListener {
  @PrePersist
  public void setSlug(Category category) {
    String prefix = getPrefix(category);
    String slug = getSlug(category, prefix);
    category.setSlug(slug);
  }

  private String getPrefix(Category category) {
    var categoryRepository = BeanUtil.getBean(CategoryRepository.class);
    return category.getParentId() == null
        ? ""
        : categoryRepository
            .findById(category.getParentId())
            .map(parent -> parent.getName() + " ")
            .orElse("");
  }

  private String getSlug(Category category, String prefix) {
    var slugify = BeanUtil.getBean(Slugify.class);
    return slugify.slugify(prefix + category.getName());
  }
}
