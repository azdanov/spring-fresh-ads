package org.js.azdanov.springfresh.events;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegistrationListener implements ApplicationListener<UserRegisteredEvent> {
  private final MessageSource messages;
  private final JavaMailSender mailSender;

  @Value("${support.email}")
  private String email;

  @Override
  public void onApplicationEvent(UserRegisteredEvent event) {
    this.sendVerificationEmail(event);
  }

  private void sendVerificationEmail(UserRegisteredEvent event) {
    var simpleMailMessage = constructEmailMessage(event);
    mailSender.send(simpleMailMessage);
  }

  private SimpleMailMessage constructEmailMessage(UserRegisteredEvent event) {
    String subject = messages.getMessage("registration.email.subject", null, event.getLocale());
    String text = messages.getMessage("registration.email.text", null, event.getLocale());

    var message = new SimpleMailMessage();
    message.setFrom(email);
    message.setTo(event.getUserDTO().email());
    message.setSubject(subject);
    message.setText("%s\r\n%s".formatted(text, event.getConfirmationURI()));
    return message;
  }
}
