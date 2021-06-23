package org.js.azdanov.springfresh.services;

import java.util.List;
import org.js.azdanov.springfresh.dtos.AreaDTO;
import org.js.azdanov.springfresh.dtos.AreaTreeDTO;

public interface AreaService {
  List<AreaTreeDTO> getAllAreasTree();

  AreaDTO findBySlug(String slug);
}
