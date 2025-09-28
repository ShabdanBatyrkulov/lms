package io.github.ShabdanBatyrkulov.lms.service;

import io.github.ShabdanBatyrkulov.lms.model.User;
import io.github.ShabdanBatyrkulov.lms.dto.UserRegistrationDto;

public interface UserService {
    User registerUser(UserRegistrationDto userData) throws IllegalArgumentException;
    User findByUsername(String username);
}
