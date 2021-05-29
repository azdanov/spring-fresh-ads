package org.js.azdanov.springfresh.services;

import java.util.List;
import org.js.azdanov.springfresh.dtos.AreaTreeDTO;
import org.js.azdanov.springfresh.models.Area;
import org.js.azdanov.springfresh.repositories.AreaRepository;
import org.springframework.stereotype.Service;
import pl.exsio.nestedj.NestedNodeRepository;
import pl.exsio.nestedj.model.Tree;

@Service
public class AreaServiceImpl implements AreaService {

  private final AreaRepository areaRepository;
  private final NestedNodeRepository<Integer, Area> areaNestedNodeRepository;

  public AreaServiceImpl(
      AreaRepository areaRepository, NestedNodeRepository<Integer, Area> areaNestedNodeRepository) {
    this.areaRepository = areaRepository;
    this.areaNestedNodeRepository = areaNestedNodeRepository;
  }

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
    String nodeName = tree.getNode().getName();
    List<Tree<Integer, Area>> children = tree.getChildren();
    return new AreaTreeDTO(nodeName, processTreeList(children));
  }

  private List<AreaTreeDTO> processTreeList(List<Tree<Integer, Area>> treeList) {
    return treeList.stream().map(this::getAreaTreeDTORecursive).toList();
  }
}
