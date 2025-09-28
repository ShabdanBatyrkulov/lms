package io.github.ShabdanBatyrkulov.lms.service;

import io.github.ShabdanBatyrkulov.lms.model.User;
import io.github.ShabdanBatyrkulov.lms.repository.UserRepository;
import io.github.ShabdanBatyrkulov.lms.dto.UserRegistrationDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(UserRegistrationDto userData) throws IllegalArgumentException {
        // Check if username already exists
        if (userRepository.findByUsername(userData.getUsername()) != null) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Hash password
        String hashedPassword = passwordEncoder.encode(userData.getPassword());

        // Create entity and save
        User user = new User(userData.getUsername(), hashedPassword);
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
