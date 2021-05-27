package org.js.azdanov.springfresh.controllers.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.js.azdanov.springfresh.validators.PasswordsMatch;
import org.js.azdanov.springfresh.validators.UserNotExist;

@UserNotExist
@PasswordsMatch
public class RegisterUserFormData {
  @NotBlank
  @Size(max = 120)
  private String name;

  @NotBlank
  @Email
  @Size(max = 120)
  private String email;

  @NotBlank private String password;
  @NotBlank private String passwordRepeated;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPasswordRepeated() {
    return passwordRepeated;
  }

  public void setPasswordRepeated(String passwordRepeated) {
    this.passwordRepeated = passwordRepeated;
  }

  @Override
  public String toString() {
    return "RegisterUserFormData{name='%s', email='%s', password='%s', passwordRepeated='%s'}"
        .formatted(name, email, password, passwordRepeated);
  }
}
