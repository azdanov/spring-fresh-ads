package org.js.azdanov.springfresh.security;

import lombok.RequiredArgsConstructor;
import org.js.azdanov.springfresh.repositories.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository
        .findByEmail(username)
        .map(
            user ->
                User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .disabled(!user.isEnabled())
                    .roles(
                        user.getRoles().stream()
                            .map(role -> role.getName().name())
                            .toArray(String[]::new))
                    .build())
        .orElseThrow(
            () -> new UsernameNotFoundException("User Not Found with username: " + username));
  }
}
