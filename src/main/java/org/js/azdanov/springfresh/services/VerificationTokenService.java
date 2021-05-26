package org.js.azdanov.springfresh.services;

import org.js.azdanov.springfresh.dtos.UserDTO;
import org.js.azdanov.springfresh.enums.TokenVerificationStatus;

public interface VerificationTokenService {
  String createVerificationTokenForUser(UserDTO userDTO);

  TokenVerificationStatus validateVerificationToken(String token);
}
