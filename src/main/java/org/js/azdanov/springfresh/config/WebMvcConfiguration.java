package org.js.azdanov.springfresh.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.js.azdanov.springfresh.interceptors.DefaultAreaInterceptor;
import org.js.azdanov.springfresh.resolvers.AreaDTOArgumentResolver;
import org.js.azdanov.springfresh.resolvers.CategoryDTOArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfiguration implements WebMvcConfigurer {
  private final AreaDTOArgumentResolver areaDTOArgumentResolver;
  private final CategoryDTOArgumentResolver categoryDTOArgumentResolver;
  private final DefaultAreaInterceptor defaultAreaInterceptor;

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(areaDTOArgumentResolver);
    resolvers.add(categoryDTOArgumentResolver);
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(defaultAreaInterceptor);
  }
}
