package org.js.azdanov.springfresh.services;

import org.js.azdanov.springfresh.dtos.UserDTO;
import org.js.azdanov.springfresh.models.User;
import org.js.azdanov.springfresh.repositories.RoleRepository;
import org.js.azdanov.springfresh.repositories.UserRepository;
import org.js.azdanov.springfresh.security.RoleAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  public UserServiceImpl(
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      RoleRepository roleRepository) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  @Transactional
  public void createUser(UserDTO userDTO) {
    var user =
        new User(userDTO.name(), userDTO.email(), passwordEncoder.encode(userDTO.password()));
    var role = roleRepository.findByRole(RoleAuthority.USER);
    user.setEnabled(true);

    if (role.isEmpty()) {
      return;
    }

    user.setRoles(Set.of(role.get()));
    userRepository.save(user);
  }
}
