package org.js.azdanov.springfresh.repositories;

import org.js.azdanov.springfresh.models.Area;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface AreaRepository extends CrudRepository<Area, Integer> {}
