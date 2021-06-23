package org.js.azdanov.springfresh.services;

import static org.js.azdanov.springfresh.config.CacheConfig.AREA_TREE;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.js.azdanov.springfresh.dtos.AreaDTO;
import org.js.azdanov.springfresh.dtos.AreaTreeDTO;
import org.js.azdanov.springfresh.exceptions.AreaNotFoundException;
import org.js.azdanov.springfresh.models.Area;
import org.js.azdanov.springfresh.repositories.AreaRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.exsio.nestedj.NestedNodeRepository;
import pl.exsio.nestedj.model.Tree;

@Service
@RequiredArgsConstructor
public class AreaServiceImpl implements AreaService {

  private final AreaRepository areaRepository;
  private final NestedNodeRepository<Integer, Area> areaNestedNodeRepository;

  @Cacheable(cacheNames = {AREA_TREE})
  @Override
  @Transactional(readOnly = true)
  public List<AreaTreeDTO> getAllAreasTree() {
    List<Area> roots = areaRepository.findAllByParentIdIsNull();

    return roots.stream()
        .map(country -> getAreaTreeDTORecursive(areaNestedNodeRepository.getTree(country)))
        .toList();
  }

  @Override
  public AreaDTO findBySlug(String slug) {
    return areaRepository
        .findBySlug(slug)
        .map(AreaDTO::new)
        .orElseThrow(AreaNotFoundException::new);
  }

  private AreaTreeDTO getAreaTreeDTORecursive(Tree<Integer, Area> tree) {
    Area area = tree.getNode();
    List<Tree<Integer, Area>> children = tree.getChildren();
    return new AreaTreeDTO(
        area.getId(), area.getName(), area.getSlug(), area.isUsable(), processTreeList(children));
  }

  private List<AreaTreeDTO> processTreeList(List<Tree<Integer, Area>> treeList) {
    return treeList.stream().map(this::getAreaTreeDTORecursive).toList();
  }
}
