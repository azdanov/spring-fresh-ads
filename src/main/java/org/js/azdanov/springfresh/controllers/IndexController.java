package org.js.azdanov.springfresh.controllers;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.js.azdanov.springfresh.dtos.AreaTreeDTO;
import org.js.azdanov.springfresh.services.AreaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class IndexController {
  private final AreaService areaService;

  @GetMapping
  public String index(Model model) {
    List<AreaTreeDTO> areas = areaService.getAllAreas();

    model.addAttribute("areas", areas);
    return "index";
  }
}
