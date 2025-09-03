package com.ironhack.IronBooking.controller;

import com.ironhack.IronBooking.dto.user.*;
import com.ironhack.IronBooking.dto.booking.*;
import com.ironhack.IronBooking.enums.UserType;
import com.ironhack.IronBooking.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
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

    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieve a list of all users in the system.")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID",
            description = "Retrieve the details of a user by their unique ID. The ID must be positive.")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new user",
            description = "Create a new user (owner or client) and return the created user with a Location header.")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO body) {
        UserResponseDTO created = userService.createUser(body);
        URI location = URI.create("/users/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user by ID", description = "Update an existing user's information by their positive ID.")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable @Positive Long id, @Valid @RequestBody UserUpdateDTO body) {
        return ResponseEntity.ok(userService.updateUser(id, body));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user by ID",
            description = "Delete a user by their unique positive ID.")
    public ResponseEntity<Void> deleteUser(@PathVariable @Positive Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Get user by email",
            description = "Retrieve a user's details by their email address.")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @GetMapping("/type/{userType}")
    @Operation(summary = "Get users by type",
            description = "Retrieve a list of users filtered by their type (OWNER or CLIENT).")
    public ResponseEntity<List<UserResponseDTO>> getUsersByType(@PathVariable UserType userType) {
        return ResponseEntity.ok(userService.getUsersByType(userType));
    }

    @GetMapping("/{id}/bookings")
    @Operation(summary = "Get bookings by user ID",
            description = "Retrieve all bookings associated with a specific user by their positive ID.")
    public ResponseEntity<List<BookingResponseDTO>> getBookingsByUserId(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(userService.getBookingsByUserId(id));
    }
}