package com.budgettrip.repository;

import com.budgettrip.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional; // <--- This import is VERY IMPORTANT

public interface UserRepository extends JpaRepository<User, Long> {

    // This must return Optional<User> to work with .orElseThrow() in the service
    Optional<User> findByUsername(String username);

    // Checks if email is already taken
    boolean existsByEmail(String email);
}