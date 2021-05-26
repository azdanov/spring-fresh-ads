package org.js.azdanov.springfresh.controllers;

import org.js.azdanov.springfresh.controllers.requests.RegisterUserFormData;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegistrationController {
  public static final String REGISTER = "register";
  public static final String REDIRECT = "redirect:/";
  public static final String REDIRECT_LOGIN = "redirect:/login";
  private final UserService userService;
  private final VerificationTokenService tokenService;
  private final ApplicationEventPublisher eventPublisher;

  public RegistrationController(
      UserService userService,
      VerificationTokenService tokenService,
      ApplicationEventPublisher eventPublisher) {
    this.userService = userService;
    this.tokenService = tokenService;
    this.eventPublisher = eventPublisher;
  }

  @GetMapping
  public String index(@AuthenticationPrincipal UserDetails userDetails, Model model) {
    if (userDetails == null) {
      model.addAttribute("user", new RegisterUserFormData());
      return REGISTER;
    } else {
      return REDIRECT;
    }
  }

  @PostMapping
  public String store(
      @Valid @ModelAttribute("user") RegisterUserFormData formData,
      BindingResult bindingResult,
      Model model,
      HttpServletRequest request,
      RedirectAttributes redirectAttributes,
      UriComponentsBuilder uriComponentsBuilder) {

    // TODO: Add password reset

    if (bindingResult.hasErrors()) {
      model.addAttribute("user", formData);
      return REGISTER;
    }

    var registeredUser = userService.createUser(new UserDTO(formData));
    var token = tokenService.createVerificationTokenForUser(registeredUser);
    var confirmationURI = getConfirmationURI(uriComponentsBuilder, token);

    eventPublisher.publishEvent(new UserRegisteredEvent(registeredUser, request.getLocale(), confirmationURI));

    redirectAttributes.addFlashAttribute("registrationSuccess", true);
    return REDIRECT_LOGIN;
  }

  private String getConfirmationURI(UriComponentsBuilder uriComponentsBuilder, String token) {
    return uriComponentsBuilder
        .uriComponents(
            MvcUriComponentsBuilder
                .fromMethodName(
                    RegistrationController.class, "confirmEmail", token, new Object())
                .build())
        .toUriString();
  }

  @GetMapping("/confirm")
  public String confirmEmail(@RequestParam String token, RedirectAttributes redirectAttributes) {
    var tokenVerificationStatus = tokenService.validateVerificationToken(token);

    // TODO: Resend token if account is disabled on login OR if token is expired

    switch (tokenVerificationStatus) {
      case TOKEN_VERIFIED -> {
        redirectAttributes.addFlashAttribute("confirmationSuccess", true);
        return REDIRECT_LOGIN;
      }
      case TOKEN_EXPIRED -> {
        redirectAttributes.addFlashAttribute("tokenExpired", true);
        return REDIRECT_LOGIN;
      }
      default -> {
        redirectAttributes.addFlashAttribute("verificationInProgress", true);
        return REDIRECT_LOGIN;
      }
    }
  }
}
