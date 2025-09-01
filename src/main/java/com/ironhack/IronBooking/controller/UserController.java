package com.ironhack.IronBooking.controller;
import com.ironhack.IronBooking.dto.user.*;
import com.ironhack.IronBooking.dto.booking.*;
import com.ironhack.IronBooking.enums.UserType;

import com.ironhack.IronBooking.service.interfaces.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    // Get all users as a list of response DTOs
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Get a user by id (id must be positive)
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // Create a new user, and return 201 Created with the Location header
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO body) {
        UserResponseDTO created = userService.createUser(body);
        URI location = URI.create("/users/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    // Update user by id (id must be positive)
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable @Positive Long id, @Valid @RequestBody UserUpdateDTO body) {
        return ResponseEntity.ok(userService.updateUser(id, body));
    }

    // Delete user by id (id must be positive)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable @Positive Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Get user by email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    // Get users by type (OWNER or CLIENT)
    @GetMapping("/type/{userType}")
    public ResponseEntity<List<UserResponseDTO>> getUsersByType(@PathVariable UserType userType) {
        // Finds all users with the specified type and maps them to response DTOs
        return ResponseEntity.ok(userService.getUsersByType(userType));
    }

    // Get all bookings for a specific user (user id must be positive)
    @GetMapping("/{id}/bookings")
    public ResponseEntity<List<BookingResponseDTO>> getBookingsByUserId(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(userService.getBookingsByUserId(id));
    }
}