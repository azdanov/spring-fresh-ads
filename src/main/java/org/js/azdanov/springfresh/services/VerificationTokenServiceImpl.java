package org.js.azdanov.springfresh.services;

import java.time.LocalDateTime;
import java.util.UUID;
import org.js.azdanov.springfresh.dtos.UserDTO;
import org.js.azdanov.springfresh.enums.TokenVerificationStatus;
import org.js.azdanov.springfresh.models.VerificationToken;
import org.js.azdanov.springfresh.repositories.UserRepository;
import org.js.azdanov.springfresh.repositories.VerificationTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {
  private final UserRepository userRepository;
  private final VerificationTokenRepository tokenRepository;

  public VerificationTokenServiceImpl(
      UserRepository userRepository, VerificationTokenRepository tokenRepository) {
    this.userRepository = userRepository;
    this.tokenRepository = tokenRepository;
  }

  @Override
  @Transactional
  public String createVerificationTokenForUser(UserDTO userDTO) {
    String token = UUID.randomUUID().toString();

    userRepository
        .findById(userDTO.id())
        .map(
            user -> {
              var verificationToken = new VerificationToken(token, user);
              return tokenRepository.save(verificationToken);
            })
        .orElseThrow();

    return token;
  }

  @Override
  @Transactional
  public TokenVerificationStatus validateVerificationToken(String token) {
    var optionalVerificationToken = tokenRepository.findByToken(token);
    if (optionalVerificationToken.isEmpty()) {
      return TokenVerificationStatus.TOKEN_INVALID;
    }

    var verificationToken = optionalVerificationToken.get();
    var user = verificationToken.getUser();

    if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
      tokenRepository.delete(verificationToken);
      return TokenVerificationStatus.TOKEN_EXPIRED;
    }

    user.setEnabled(true);
    userRepository.save(user);
    tokenRepository.delete(verificationToken);
    return TokenVerificationStatus.TOKEN_VERIFIED;
  }
}
