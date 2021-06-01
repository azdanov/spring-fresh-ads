package org.js.azdanov.springfresh.config;

import java.util.List;
import org.js.azdanov.springfresh.interceptors.DefaultAreaInterceptor;
import org.js.azdanov.springfresh.resolvers.AreaDTOArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
  private final AreaDTOArgumentResolver areaDTOArgumentResolver;
  private final DefaultAreaInterceptor defaultAreaInterceptor;

  public WebMvcConfiguration(
      AreaDTOArgumentResolver areaDTOArgumentResolver,
      DefaultAreaInterceptor defaultAreaInterceptor) {
    this.areaDTOArgumentResolver = areaDTOArgumentResolver;
    this.defaultAreaInterceptor = defaultAreaInterceptor;
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(areaDTOArgumentResolver);
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(defaultAreaInterceptor);
  }
}
