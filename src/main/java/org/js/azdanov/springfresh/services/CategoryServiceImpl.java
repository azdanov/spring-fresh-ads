package org.js.azdanov.springfresh.services;

import static org.js.azdanov.springfresh.config.CacheConfig.CATEGORY;

import java.util.List;
import org.js.azdanov.springfresh.dtos.CategoryTreeDTO;
import org.js.azdanov.springfresh.models.Category;
import org.js.azdanov.springfresh.repositories.CategoryRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.exsio.nestedj.NestedNodeRepository;
import pl.exsio.nestedj.model.Tree;

@Service
@CacheConfig(cacheNames = {CATEGORY})
public class CategoryServiceImpl implements CategoryService {
  private final CategoryRepository categoryRepository;
  private final NestedNodeRepository<Integer, Category> categoryNestedNodeRepository;

  public CategoryServiceImpl(
      CategoryRepository categoryRepository,
      NestedNodeRepository<Integer, Category> categoryNestedNodeRepository) {
    this.categoryRepository = categoryRepository;
    this.categoryNestedNodeRepository = categoryNestedNodeRepository;
  }

  @Cacheable
  @Transactional(readOnly = true)
  public List<CategoryTreeDTO> getAllCategories() {
    List<Category> roots = categoryRepository.findAllByParentIdIsNull();

    return roots.stream()
        .map(
            category -> getCategoryTreeDTORecursive(categoryNestedNodeRepository.getTree(category)))
        .toList();
  }

  private CategoryTreeDTO getCategoryTreeDTORecursive(Tree<Integer, Category> tree) {
    Category category = tree.getNode();
    List<Tree<Integer, Category>> children = tree.getChildren();
    return new CategoryTreeDTO(
        category.getName(), category.getSlug(), category.getPrice(), processTreeList(children));
  }

  private List<CategoryTreeDTO> processTreeList(List<Tree<Integer, Category>> treeList) {
    return treeList.stream().map(this::getCategoryTreeDTORecursive).toList();
  }
}
