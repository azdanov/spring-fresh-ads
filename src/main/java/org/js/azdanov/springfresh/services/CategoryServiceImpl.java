package org.js.azdanov.springfresh.services;

import static org.js.azdanov.springfresh.config.CacheConfig.CATEGORY_LISTING;
import static org.js.azdanov.springfresh.config.CacheConfig.CATEGORY_TREE;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.js.azdanov.springfresh.dtos.AreaDTO;
import org.js.azdanov.springfresh.dtos.CategoryDTO;
import org.js.azdanov.springfresh.dtos.CategoryListingTreeDTO;
import org.js.azdanov.springfresh.dtos.CategoryTreeDTO;
import org.js.azdanov.springfresh.exceptions.CategoryNotFoundException;
import org.js.azdanov.springfresh.models.Category;
import org.js.azdanov.springfresh.models.Listing;
import org.js.azdanov.springfresh.repositories.AreaRepository;
import org.js.azdanov.springfresh.repositories.CategoryRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.exsio.nestedj.NestedNodeRepository;
import pl.exsio.nestedj.model.Tree;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
  private final CategoryRepository categoryRepository;
  private final AreaRepository areaRepository;
  private final NestedNodeRepository<Integer, Category> categoryNestedNodeRepository;

  @Cacheable(cacheNames = {CATEGORY_TREE})
  @Override
  @Transactional(readOnly = true)
  public List<CategoryTreeDTO> getAllCategories() {
    List<Category> roots = categoryRepository.findAllByParentIdIsNull();

    return roots.stream()
        .map(
            category -> getCategoryTreeDTORecursive(categoryNestedNodeRepository.getTree(category)))
        .toList();
  }

  // Needs cache to prevent NestedJ n+1
  // TODO: Find a way to reduce n+1
  @Cacheable(
      cacheNames = {CATEGORY_LISTING},
      key = "#areaDTO.slug")
  @Transactional(readOnly = true)
  public List<CategoryListingTreeDTO> getAllCategoriesWithListingCount(AreaDTO areaDTO) {
    List<Category> roots = categoryRepository.findAllByParentIdIsNull();
    List<Integer> areaIds = areaRepository.findInclusiveChildrenIds(areaDTO.slug());

    return roots.stream()
        .map(
            category ->
                getCategoryListingTreeDTORecursive(
                    categoryNestedNodeRepository.getTree(category), areaIds))
        .toList();
  }

  @Override
  public CategoryDTO findBySlug(String slug) {
    return categoryRepository
        .findBySlug(slug)
        .map(CategoryDTO::new)
        .orElseThrow(CategoryNotFoundException::new);
  }

  @Override
  public CategoryTreeDTO getParent(CategoryDTO categoryDTO) {
    Category category =
        categoryRepository
            .findBySlug(categoryDTO.slug())
            .orElseThrow(CategoryNotFoundException::new);

    CategoryTreeDTO categoryTreeDTO =
        new CategoryTreeDTO(
            category.getId(),
            category.getName(),
            category.getSlug(),
            category.getPrice(),
            category.isUsable(),
            null);
    return categoryNestedNodeRepository
        .getParent(category)
        .map(
            categoryParent ->
                new CategoryTreeDTO(
                    categoryParent.getId(),
                    categoryParent.getName(),
                    categoryParent.getSlug(),
                    categoryParent.getPrice(),
                    categoryParent.isUsable(),
                    List.of(categoryTreeDTO)))
        .orElse(categoryTreeDTO);
  }

  private CategoryListingTreeDTO getCategoryListingTreeDTORecursive(
      Tree<Integer, Category> tree, List<Integer> areaIds) {
    Category category = tree.getNode();
    long listingSize = getListingSize(category, areaIds);
    List<Tree<Integer, Category>> children = tree.getChildren();
    return new CategoryListingTreeDTO(
        category.getName(),
        category.getSlug(),
        category.getPrice(),
        listingSize,
        processCategoryListingTreeList(children, areaIds));
  }

  private long getListingSize(Category category, List<Integer> areaIds) {
    List<Listing> listings = category.getListings();
    return listings.stream()
        .filter(listing -> listing.isLive() && areaIds.contains(listing.getArea().getId()))
        .count();
  }

  private List<CategoryListingTreeDTO> processCategoryListingTreeList(
      List<Tree<Integer, Category>> treeList, List<Integer> areaIds) {
    return treeList.stream()
        .map(tree -> getCategoryListingTreeDTORecursive(tree, areaIds))
        .toList();
  }

  private CategoryTreeDTO getCategoryTreeDTORecursive(Tree<Integer, Category> tree) {
    Category category = tree.getNode();
    List<Tree<Integer, Category>> children = tree.getChildren();
    return new CategoryTreeDTO(
        category.getId(),
        category.getName(),
        category.getSlug(),
        category.getPrice(),
        category.isUsable(),
        processCategoryTreeList(children));
  }

  private List<CategoryTreeDTO> processCategoryTreeList(List<Tree<Integer, Category>> treeList) {
    return treeList.stream().map(this::getCategoryTreeDTORecursive).toList();
  }
}
