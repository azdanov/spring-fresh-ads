package org.js.azdanov.springfresh.controllers.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.js.azdanov.springfresh.validators.PasswordsMatch;
import org.js.azdanov.springfresh.validators.UserNotExist;

@UserNotExist
@PasswordsMatch
@Getter
@Setter
public class RegisterUserForm {
  @NotBlank
  @Size(max = 120)
  private String name;

  @NotBlank
  @Email
  @Size(max = 120)
  private String email;

  @NotBlank private String password;
  @NotBlank private String passwordRepeated;

  @Override
  public String toString() {
    return "RegisterUserForm{name='%s', email='%s'}".formatted(name, email);
  }
}
