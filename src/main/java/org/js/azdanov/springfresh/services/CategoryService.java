package org.js.azdanov.springfresh.services;

import java.util.List;
import org.js.azdanov.springfresh.dtos.CategoryDTO;
import org.js.azdanov.springfresh.dtos.CategoryTreeDTO;

public interface CategoryService {
  List<CategoryTreeDTO> getAllCategories();

  CategoryDTO findBySlug(String slug);

  CategoryTreeDTO getParent(CategoryDTO categoryDTO);
}
