package org.js.azdanov.springfresh.repositories;

import java.util.Optional;
import org.js.azdanov.springfresh.models.VerificationToken;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Integer> {
  @EntityGraph(attributePaths = {"user"})
  Optional<VerificationToken> findByToken(String token);
}
