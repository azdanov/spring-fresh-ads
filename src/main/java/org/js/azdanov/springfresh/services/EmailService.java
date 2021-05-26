package org.js.azdanov.springfresh.services;

import org.js.azdanov.springfresh.dtos.UserDTO;

import javax.mail.MessagingException;

public interface EmailService {
  void sendVerifyEmail(UserDTO userDTO) throws MessagingException;
}
