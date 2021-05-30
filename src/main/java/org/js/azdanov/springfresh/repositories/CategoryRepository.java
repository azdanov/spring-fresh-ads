package org.js.azdanov.springfresh.repositories;

import org.js.azdanov.springfresh.models.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {}
