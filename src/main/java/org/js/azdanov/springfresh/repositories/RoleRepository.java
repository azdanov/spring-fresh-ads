package org.js.azdanov.springfresh.repositories;

import java.util.Optional;
import org.js.azdanov.springfresh.models.Role;
import org.js.azdanov.springfresh.security.RoleAuthority;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface RoleRepository extends CrudRepository<Role, Integer> {
  @EntityGraph(attributePaths = {"users"})
  Optional<Role> findByRole(RoleAuthority role);
}
