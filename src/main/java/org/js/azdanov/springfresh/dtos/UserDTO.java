package org.js.azdanov.springfresh.dtos;

import org.js.azdanov.springfresh.controllers.requests.RegisterUserFormData;

public record UserDTO(Integer id, String name, String email, String password) {
  public UserDTO(RegisterUserFormData userRequest) {
    this(null, userRequest.getName(), userRequest.getEmail(), userRequest.getPassword());
  }
}
