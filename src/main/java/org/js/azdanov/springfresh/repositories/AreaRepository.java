package org.js.azdanov.springfresh.repositories;

import java.util.List;
import java.util.Optional;
import org.js.azdanov.springfresh.models.Area;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface AreaRepository extends CrudRepository<Area, Integer> {
  List<Area> findAllByParentIdIsNull();

  Optional<Area> findBySlug(String slug);

  @Query(
      "select a.id from Area a where a.treeLeft"
          + " between (select ac.treeLeft from Area ac where ac.slug = :slug)"
          + " and (select ac.treeRight from Area ac where ac.slug = :slug)"
          + " order by a.id")
  List<Integer> findInclusiveChildrenIds(String slug);
}
