package org.js.azdanov.springfresh.services;

import java.util.List;
import org.js.azdanov.springfresh.dtos.AreaDTO;
import org.js.azdanov.springfresh.dtos.CategoryDTO;
import org.js.azdanov.springfresh.dtos.CategoryListingTreeDTO;
import org.js.azdanov.springfresh.dtos.CategoryTreeDTO;

public interface CategoryService {
  List<CategoryTreeDTO> getAllCategories();

  List<CategoryListingTreeDTO> getAllCategoriesWithListingCount(AreaDTO areaDTO);

  CategoryDTO findBySlug(String slug);

  CategoryTreeDTO getParent(CategoryDTO categoryDTO);
}
