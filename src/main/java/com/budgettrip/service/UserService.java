package com.budgettrip.service;

import com.budgettrip.dto.UserRegistrationDto;
import com.budgettrip.entity.Role;
import com.budgettrip.entity.User;
import com.budgettrip.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(UserRegistrationDto dto) {
        // 1. Create a new User entity
        User user = new User();

        // 2. Map data from DTO to Entity
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());

        // 3. Encrypt the password (Critical step!)
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        // 4. Set default values
        user.setRole(Role.USER); // ✅ මේක හරි// Default role is always TRAVELER
        user.setPreference(dto.getPreference()); // Optional preference

        // 5. Save to database
        userRepository.save(user);
    }
}