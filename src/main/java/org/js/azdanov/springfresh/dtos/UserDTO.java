package org.js.azdanov.springfresh.dtos;

import org.js.azdanov.springfresh.controllers.requests.RegisterUserForm;

public record UserDTO(Integer id, String name, String email, String password, Boolean enabled) {
  public UserDTO(RegisterUserForm userRequest) {
    this(null, userRequest.getName(), userRequest.getEmail(), userRequest.getPassword(), null);
  }
}
