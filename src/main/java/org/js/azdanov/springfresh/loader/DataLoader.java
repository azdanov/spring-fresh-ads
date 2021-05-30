package org.js.azdanov.springfresh.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import org.apache.commons.collections4.ListUtils;
import org.js.azdanov.springfresh.dtos.AreaTreeDTO;
import org.js.azdanov.springfresh.dtos.CategoryTreeDTO;
import org.js.azdanov.springfresh.models.Area;
import org.js.azdanov.springfresh.models.Category;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import pl.exsio.nestedj.NestedNodeRepository;

@Component
@ConditionalOnProperty(value = "dataloader.enabled", havingValue = "true")
@Transactional
public class DataLoader implements CommandLineRunner {
  private final NestedNodeRepository<Integer, Area> areaNestedNodeRepository;
  private final NestedNodeRepository<Integer, Category> categoryNestedNodeRepository;
  private final ObjectMapper mapper;

  public DataLoader(
      NestedNodeRepository<Integer, Area> areaNestedNodeRepository,
      NestedNodeRepository<Integer, Category> categoryNestedNodeRepository) {
    this.areaNestedNodeRepository = areaNestedNodeRepository;
    this.categoryNestedNodeRepository = categoryNestedNodeRepository;
    this.mapper = new ObjectMapper();
  }

  @Override
  public void run(String... args) throws Exception {
    loadAreas();
    loadCategories();
  }

  private void loadAreas() throws IOException {
    List<AreaTreeDTO> countries =
        mapper.readValue(
            ResourceUtils.getFile("classpath:data/areas.json"),
            mapper.getTypeFactory().constructCollectionType(List.class, AreaTreeDTO.class));
    countries.forEach(createAreaRoot());
  }

  private Consumer<AreaTreeDTO> createAreaRoot() {
    return areaTreeDTO -> {
      Area country = new Area(areaTreeDTO.name());
      areaNestedNodeRepository.insertAsLastRoot(country);
      ListUtils.emptyIfNull(areaTreeDTO.children()).forEach(createAreaNode(country));
    };
  }

  private Consumer<AreaTreeDTO> createAreaNode(Area parent) {
    return areaTreeDTO -> {
      Area node = new Area(areaTreeDTO.name());
      areaNestedNodeRepository.insertAsLastChildOf(node, parent);
      ListUtils.emptyIfNull(areaTreeDTO.children()).forEach(createAreaNode(node));
    };
  }

  private void loadCategories() throws IOException {
    List<CategoryTreeDTO> categories =
        mapper.readValue(
            ResourceUtils.getFile("classpath:data/categories.json"),
            mapper.getTypeFactory().constructCollectionType(List.class, CategoryTreeDTO.class));
    categories.forEach(createCategoryRoot());
  }

  private Consumer<CategoryTreeDTO> createCategoryRoot() {
    return categoryTreeDTO -> {
      Category category = new Category(categoryTreeDTO.name());
      categoryNestedNodeRepository.insertAsLastRoot(category);
      ListUtils.emptyIfNull(categoryTreeDTO.children()).forEach(createCategoryNode(category));
    };
  }

  private Consumer<CategoryTreeDTO> createCategoryNode(Category parent) {
    return categoryTreeDTO -> {
      Category node = new Category(categoryTreeDTO.name());
      categoryNestedNodeRepository.insertAsLastChildOf(node, parent);
      ListUtils.emptyIfNull(categoryTreeDTO.children()).forEach(createCategoryNode(node));
    };
  }
}
