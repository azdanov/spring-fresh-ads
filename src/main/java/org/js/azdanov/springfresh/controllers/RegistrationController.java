package org.js.azdanov.springfresh.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.js.azdanov.springfresh.controllers.requests.RegisterUserForm;
import org.js.azdanov.springfresh.dtos.UserDTO;
import org.js.azdanov.springfresh.events.UserRegisteredEvent;
import org.js.azdanov.springfresh.services.UserService;
import org.js.azdanov.springfresh.services.VerificationTokenService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
  private final UserService userService;
  private final VerificationTokenService tokenService;
  private final ApplicationEventPublisher eventPublisher;

  @GetMapping("/register")
  public String index(@AuthenticationPrincipal UserDetails userDetails, Model model) {
    if (userDetails == null) {
      model.addAttribute("registerUserForm", new RegisterUserForm());
      return "auth/register";
    } else {
      return "redirect:/";
    }
  }

  @PostMapping("/register")
  public String store(
      @Valid @ModelAttribute("registerUserForm") RegisterUserForm registerUserForm,
      BindingResult bindingResult,
      Model model,
      HttpServletRequest request,
      RedirectAttributes redirectAttributes,
      UriComponentsBuilder uriComponentsBuilder) {

    if (bindingResult.hasErrors()) {
      model.addAttribute("registerUserForm", registerUserForm);
      return "auth/register";
    }

    var registeredUser = userService.createUser(new UserDTO(registerUserForm));
    var token = tokenService.createVerificationTokenForUser(registeredUser);
    var confirmationURI = getConfirmationURI(uriComponentsBuilder, token);

    eventPublisher.publishEvent(
        new UserRegisteredEvent(registeredUser, request.getLocale(), confirmationURI));
    redirectAttributes.addFlashAttribute("confirmationURI", confirmationURI);

    redirectAttributes.addFlashAttribute("registrationSuccess", true);
    return "redirect:/login";
  }

  private String getConfirmationURI(UriComponentsBuilder uriComponentsBuilder, String token) {
    return uriComponentsBuilder
        .uriComponents(
            MvcUriComponentsBuilder.fromMethodName(
                    RegistrationController.class, "confirmEmail", token, new Object())
                .build())
        .toUriString();
  }

  @GetMapping("/register/confirm")
  public String confirmEmail(@RequestParam String token, RedirectAttributes redirectAttributes) {
    var tokenVerificationStatus = tokenService.validateVerificationToken(token);

    switch (tokenVerificationStatus) {
      case TOKEN_VERIFIED -> {
        redirectAttributes.addFlashAttribute("confirmationSuccess", true);
        return "redirect:/login";
      }
      case TOKEN_EXPIRED -> {
        redirectAttributes.addFlashAttribute("tokenExpired", true);
        return "redirect:/login";
      }
      default -> {
        redirectAttributes.addFlashAttribute("verificationInProgress", true);
        return "redirect:/login";
      }
    }
  }
}
