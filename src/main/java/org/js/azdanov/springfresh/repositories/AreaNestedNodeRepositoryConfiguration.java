package org.js.azdanov.springfresh.repositories;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.js.azdanov.springfresh.models.Area;
import org.js.azdanov.springfresh.models.AreaTreeDiscriminator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.exsio.nestedj.NestedNodeRepository;
import pl.exsio.nestedj.config.jpa.JpaNestedNodeRepositoryConfiguration;
import pl.exsio.nestedj.config.jpa.factory.JpaNestedNodeRepositoryFactory;

@Configuration
public class AreaNestedNodeRepositoryConfiguration {
  @PersistenceContext private EntityManager entityManager;

  @Bean
  public NestedNodeRepository<Integer, Area> areaNestedNodeRepository() {

    JpaNestedNodeRepositoryConfiguration<Integer, Area> configuration =
        new JpaNestedNodeRepositoryConfiguration<>(
            entityManager, Area.class, Integer.class, new AreaTreeDiscriminator());

    return JpaNestedNodeRepositoryFactory.create(configuration);
  }
}
