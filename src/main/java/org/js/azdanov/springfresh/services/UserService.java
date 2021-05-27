package org.js.azdanov.springfresh.services;

import org.js.azdanov.springfresh.dtos.UserDTO;

public interface UserService {
  UserDTO createUser(UserDTO userDTO);

  boolean userExistsByEmail(String email);
}
