package org.js.azdanov.springfresh.resolvers;

import org.js.azdanov.springfresh.dtos.AreaDTO;
import org.js.azdanov.springfresh.services.AreaService;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class AreaDTOArgumentResolver implements HandlerMethodArgumentResolver {
  private final AreaService areaService;

  public AreaDTOArgumentResolver(AreaService areaService) {
    this.areaService = areaService;
  }

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameter().getType() == AreaDTO.class;
  }

  @Override
  public Object resolveArgument(
      MethodParameter parameter,
      ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest,
      WebDataBinderFactory binderFactory) {

    String requestPath = ((ServletWebRequest) webRequest).getRequest().getServletPath();

    String slug = requestPath.substring(0, requestPath.indexOf("/", 1)).replaceAll("^/", "");

    return areaService.findBySlug(slug);
  }
}
