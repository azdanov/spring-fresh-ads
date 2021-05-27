package org.js.azdanov.springfresh.services;

import java.util.UUID;
import javax.mail.MessagingException;
import org.js.azdanov.springfresh.dtos.UserDTO;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
  private final JavaMailSender javaMailSender;

  public EmailServiceImpl(JavaMailSender javaMailSender) {
    this.javaMailSender = javaMailSender;
  }

  @Override
  public void sendVerifyEmail(UserDTO userDTO) throws MessagingException {
    var message = javaMailSender.createMimeMessage();

    var helper = new MimeMessageHelper(message);
    helper.setFrom("noreply@fresh-ads.com");
    helper.setTo("test@host.com");
    helper.setSubject("sample subject " + UUID.randomUUID());
    helper.setText("Please <a href=\"#\">verify your account</a> to login.", true);

    javaMailSender.send(message);
  }
}
