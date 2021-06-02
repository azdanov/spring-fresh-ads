package org.js.azdanov.springfresh.services;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.js.azdanov.springfresh.dtos.UserDTO;
import org.js.azdanov.springfresh.exceptions.RoleNotFoundException;
import org.js.azdanov.springfresh.exceptions.UserAlreadyExistsException;
import org.js.azdanov.springfresh.models.User;
import org.js.azdanov.springfresh.repositories.RoleRepository;
import org.js.azdanov.springfresh.repositories.UserRepository;
import org.js.azdanov.springfresh.security.RoleAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public UserDTO createUser(UserDTO userDTO) {
    if (userRepository.existsByEmail(userDTO.email())) {
      throw new UserAlreadyExistsException(
          "There is an account with that email address: %s".formatted(userDTO.email()));
    }

    var user =
        new User(userDTO.name(), userDTO.email(), passwordEncoder.encode(userDTO.password()));
    var role = roleRepository.findByName(RoleAuthority.USER);

    if (role.isEmpty()) {
      throw new RoleNotFoundException("Role not found");
    }

    user.setRoles(Set.of(role.get()));
    user = userRepository.save(user);

    return new UserDTO(
        user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.isEnabled());
  }

  @Override
  public boolean userExistsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }
}
