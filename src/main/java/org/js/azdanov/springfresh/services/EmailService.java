package org.js.azdanov.springfresh.services;

import javax.mail.MessagingException;
import org.js.azdanov.springfresh.dtos.UserDTO;

public interface EmailService {
  void sendVerifyEmail(UserDTO userDTO) throws MessagingException;
}
