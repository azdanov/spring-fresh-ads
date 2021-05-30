package org.js.azdanov.springfresh.services;

import static org.js.azdanov.springfresh.config.CacheConfig.AREA;

import java.util.List;
import org.js.azdanov.springfresh.dtos.AreaTreeDTO;
import org.js.azdanov.springfresh.models.Area;
import org.js.azdanov.springfresh.repositories.AreaRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.exsio.nestedj.NestedNodeRepository;
import pl.exsio.nestedj.model.Tree;

@Service
@CacheConfig(cacheNames = {AREA})
public class AreaServiceImpl implements AreaService {

  private final AreaRepository areaRepository;
  private final NestedNodeRepository<Integer, Area> areaNestedNodeRepository;

  public AreaServiceImpl(
      AreaRepository areaRepository, NestedNodeRepository<Integer, Area> areaNestedNodeRepository) {
    this.areaRepository = areaRepository;
    this.areaNestedNodeRepository = areaNestedNodeRepository;
  }

  @Cacheable
  @Override
  public List<AreaTreeDTO> getAllAreas() {
    List<Area> roots = areaRepository.findAllByParentIdIsNull();

    return roots.stream()
        .map(
            country -> {
              Tree<Integer, Area> tree = areaNestedNodeRepository.getTree(country);
              return getAreaTreeDTORecursive(tree);
            })
        .toList();
  }

  private AreaTreeDTO getAreaTreeDTORecursive(Tree<Integer, Area> tree) {
    Area area = tree.getNode();
    List<Tree<Integer, Area>> children = tree.getChildren();
    return new AreaTreeDTO(area.getName(), area.getSlug(), processTreeList(children));
  }

  private List<AreaTreeDTO> processTreeList(List<Tree<Integer, Area>> treeList) {
    return treeList.stream().map(this::getAreaTreeDTORecursive).toList();
  }
}