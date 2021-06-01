package org.js.azdanov.springfresh.controllers;

import static org.js.azdanov.springfresh.config.SessionConfig.CURRENT_AREA;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.js.azdanov.springfresh.dtos.AreaDTO;
import org.js.azdanov.springfresh.dtos.CategoryTreeDTO;
import org.js.azdanov.springfresh.services.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/{areaSlug}")
public class CategoryController {
  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping("/categories")
  public String index(AreaDTO areaDTO, Model model, HttpSession session) {
    session.setAttribute(CURRENT_AREA, areaDTO);

    List<CategoryTreeDTO> categories = categoryService.getAllCategories();
    model.addAttribute("categories", categories);
    return "categories";
  }
}
