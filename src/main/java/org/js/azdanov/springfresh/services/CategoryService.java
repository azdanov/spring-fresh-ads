package org.js.azdanov.springfresh.services;

import java.util.List;
import org.js.azdanov.springfresh.dtos.CategoryTreeDTO;

public interface CategoryService {
  List<CategoryTreeDTO> getAllCategories();
}
