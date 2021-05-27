package org.js.azdanov.springfresh.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.js.azdanov.springfresh.controllers.requests.RegisterUserFormData;

public class PasswordsMatchValidator
    implements ConstraintValidator<PasswordsMatch, RegisterUserFormData> {
  @Override
  public void initialize(PasswordsMatch constraintAnnotation) {
    // intentionally empty
  }

  @Override
  public boolean isValid(RegisterUserFormData value, ConstraintValidatorContext context) {
    if (value.getPassword().equals(value.getPasswordRepeated())) {
      return true;
    }

    context.disableDefaultConstraintViolation();
    context
        .buildConstraintViolationWithTemplate("{PasswordsNotMatching}")
        .addPropertyNode("passwordRepeated")
        .addConstraintViolation();
    return false;
  }
}
