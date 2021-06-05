package org.js.azdanov.springfresh.controllers;

import static org.js.azdanov.springfresh.config.SessionConfig.CURRENT_AREA;

import java.util.List;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.js.azdanov.springfresh.dtos.AreaDTO;
import org.js.azdanov.springfresh.dtos.CategoryListingTreeDTO;
import org.js.azdanov.springfresh.services.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/{areaSlug}")
@RequiredArgsConstructor
public class CategoryController {
  private final CategoryService categoryService;

  @GetMapping("/categories")
  public String index(AreaDTO areaDTO, Model model, HttpSession session) {
    session.setAttribute(CURRENT_AREA, areaDTO);

    List<CategoryListingTreeDTO> categories = categoryService.getAllCategoriesWithListingCount(areaDTO);
    model.addAttribute("categories", categories);
    return "categories";
  }
}
