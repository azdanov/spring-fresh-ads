package org.js.azdanov.springfresh.config;

import java.time.Duration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {
  public static final String AREA_TREE = "area_tree";
  public static final String CATEGORY_LISTING = "category_listing";
  public static final String CATEGORY_TREE = "category_tree";

  private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration =
      Eh107Configuration.fromEhcacheCacheConfiguration(
          CacheConfigurationBuilder.newCacheConfigurationBuilder(
                  Object.class, Object.class, ResourcePoolsBuilder.heap(100))
              .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(30)))
              .build());

  @Bean
  public JCacheManagerCustomizer cacheManagerCustomizer() {
    return cm -> {
      createCache(cm, AREA_TREE);
      createCache(cm, CATEGORY_TREE);
      createCache(cm, CATEGORY_LISTING);
    };
  }

  private void createCache(javax.cache.CacheManager cm, String cacheName) {
    javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
    if (cache != null) {
      cache.clear();
    } else {
      cm.createCache(cacheName, jcacheConfiguration);
    }
  }
}
