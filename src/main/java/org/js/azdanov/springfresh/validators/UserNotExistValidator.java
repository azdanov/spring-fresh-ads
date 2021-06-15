package org.js.azdanov.springfresh.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.js.azdanov.springfresh.controllers.requests.RegisterUserForm;
import org.js.azdanov.springfresh.services.UserService;

@RequiredArgsConstructor
public class UserNotExistValidator implements ConstraintValidator<UserNotExist, RegisterUserForm> {
  private final UserService userService;

  public void initialize(UserNotExist constraint) {
    // intentionally empty
  }

  public boolean isValid(RegisterUserForm formData, ConstraintValidatorContext context) {
    if (!userService.userExistsByEmail(formData.getEmail())) {
      return true;
    }

    context.disableDefaultConstraintViolation();
    context
        .buildConstraintViolationWithTemplate("{UserAlreadyExists}")
        .addPropertyNode("email")
        .addConstraintViolation();
    return false;
  }
}
