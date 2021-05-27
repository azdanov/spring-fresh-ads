package org.js.azdanov.springfresh.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.js.azdanov.springfresh.controllers.requests.RegisterUserFormData;
import org.js.azdanov.springfresh.services.UserService;

public class UserNotExistValidator
    implements ConstraintValidator<UserNotExist, RegisterUserFormData> {
  private final UserService userService;

  public UserNotExistValidator(UserService userService) {
    this.userService = userService;
  }

  public void initialize(UserNotExist constraint) {
    // intentionally empty
  }

  public boolean isValid(RegisterUserFormData formData, ConstraintValidatorContext context) {
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
