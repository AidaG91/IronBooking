package com.ironhack.IronBooking.repository;

import com.ironhack.IronBooking.enums.UserType;
import com.ironhack.IronBooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Search by email (login or validation)
    Optional<User> findByEmail(String email);

    // Check existence by email (registration)
    boolean existsByEmail(String email);

    // Search all users by type (admin panel per example)
    List<User> findByUserType(UserType userType);
}
