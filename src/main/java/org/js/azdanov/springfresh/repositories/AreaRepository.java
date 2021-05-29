package org.js.azdanov.springfresh.repositories;

import java.util.List;
import org.js.azdanov.springfresh.models.Area;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface AreaRepository extends CrudRepository<Area, Integer> {
  List<Area> findAllByParentIdIsNull();
}
