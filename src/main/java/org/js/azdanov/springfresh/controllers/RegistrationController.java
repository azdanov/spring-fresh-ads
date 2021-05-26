package org.js.azdanov.springfresh.controllers;

import org.js.azdanov.springfresh.controllers.requests.RegisterUserFormData;
import org.js.azdanov.springfresh.dtos.UserDTO;
import org.js.azdanov.springfresh.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegistrationController {
  private final UserService userService;

  public RegistrationController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public String index(@AuthenticationPrincipal UserDetails userDetails, Model model) {
    if (userDetails == null) {
      model.addAttribute("user", new RegisterUserFormData());
      return "register";
    } else {
      return "redirect:/";
    }
  }

  @PostMapping
  public String store(
      @Valid @ModelAttribute("user") RegisterUserFormData formData,
      BindingResult bindingResult,
      Model model) {
    if (bindingResult.hasErrors()) {
      model.addAttribute("user", formData);
      return "register";
    }

    userService.createUser(new UserDTO(formData));

    return "redirect:/login";
  }
}
