package org.js.azdanov.springfresh.controllers;

import java.util.List;
import org.js.azdanov.springfresh.dtos.CategoryTreeDTO;
import org.js.azdanov.springfresh.services.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/{areaSlug}")
public class CategoryController {
  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping("/categories")
  public String index(@PathVariable String areaSlug, Model model) {
    System.out.println(areaSlug);
    List<CategoryTreeDTO> categories = categoryService.getAllCategories();
    model.addAttribute("categories", categories);
    return "categories";
  }
}
