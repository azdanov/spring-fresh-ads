package org.js.azdanov.springfresh.interceptors;

import static org.js.azdanov.springfresh.config.SessionConfig.CURRENT_AREA;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.js.azdanov.springfresh.dtos.AreaDTO;
import org.js.azdanov.springfresh.services.AreaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class DefaultAreaInterceptor implements HandlerInterceptor {
  private final AreaService areaService;

  @Value("${default.current-area}")
  private String currentArea;

  public DefaultAreaInterceptor(AreaService areaService) {
    this.areaService = areaService;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    HttpSession session = request.getSession();

    if (session.getAttribute(CURRENT_AREA) == null) {
      AreaDTO area = areaService.findBySlug(currentArea);
      session.setAttribute(CURRENT_AREA, area);
    }

    return HandlerInterceptor.super.preHandle(request, response, handler);
  }
}
