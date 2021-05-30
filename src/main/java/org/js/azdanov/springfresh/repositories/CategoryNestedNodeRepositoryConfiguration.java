package org.js.azdanov.springfresh.repositories;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.js.azdanov.springfresh.models.Category;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.exsio.nestedj.NestedNodeRepository;
import pl.exsio.nestedj.config.jpa.JpaNestedNodeRepositoryConfiguration;
import pl.exsio.nestedj.config.jpa.factory.JpaNestedNodeRepositoryFactory;

@Configuration
public class CategoryNestedNodeRepositoryConfiguration {
  @PersistenceContext private EntityManager entityManager;

  @Bean
  public NestedNodeRepository<Integer, Category> categoryNestedNodeRepository() {

    JpaNestedNodeRepositoryConfiguration<Integer, Category> configuration =
        new JpaNestedNodeRepositoryConfiguration<>(entityManager, Category.class, Integer.class);

    return JpaNestedNodeRepositoryFactory.create(configuration);
  }
}
