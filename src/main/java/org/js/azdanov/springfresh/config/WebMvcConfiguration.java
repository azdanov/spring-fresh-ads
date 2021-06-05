package org.js.azdanov.springfresh.config;

import lombok.RequiredArgsConstructor;
import org.js.azdanov.springfresh.interceptors.DefaultAreaInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfiguration implements WebMvcConfigurer {
  private final DefaultAreaInterceptor defaultAreaInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(defaultAreaInterceptor);
  }
}
