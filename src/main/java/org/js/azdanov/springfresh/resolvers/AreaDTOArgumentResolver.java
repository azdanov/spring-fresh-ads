package org.js.azdanov.springfresh.resolvers;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.js.azdanov.springfresh.dtos.AreaDTO;
import org.js.azdanov.springfresh.services.AreaService;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;

@Component
@RequiredArgsConstructor
public class AreaDTOArgumentResolver implements HandlerMethodArgumentResolver {
  private final AreaService areaService;

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

    Map<String, String> pathVariables = getPathVariables(webRequest);
    return areaService.findBySlug(pathVariables.get("areaSlug"));
  }

  private Map<String, String> getPathVariables(NativeWebRequest webRequest) {
    HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
    return (Map<String, String>)
        httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
  }
}
