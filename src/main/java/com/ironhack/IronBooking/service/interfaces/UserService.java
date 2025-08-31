package com.ironhack.IronBooking.service.interfaces;
import com.ironhack.IronBooking.enums.UserType;
import com.ironhack.IronBooking.dto.user.*;

import java.util.List;

// Defines the contract for user-related business logic.
// Provides methods for creating, retrieving, updating, and deleting users.
public interface UserService {

    // Creates a new user from the provided request DTO and returns the response DTO.
    UserResponseDTO createUser(UserRequestDTO userRequestDto);

    // Retrieves a user by their unique ID and returns the response DTO.
    UserResponseDTO getUserById(Long id);

    // Retrieves all users and returns a list of response DTOs.
    List<UserResponseDTO> getAllUsers();

    // Updates an existing user with the given ID using the provided update DTO and returns the response DTO.
    UserResponseDTO updateUser(Long id, UserUpdateDTO userUpdateDTO);

    // Deletes a user by their unique ID.
    void deleteUser(Long id);

    // Retrieves a user by their email address and returns the response DTO.
    UserResponseDTO getUserByEmail(String email);

    // Retrieves a list of users by their UserType and returns a list of response DTOs.
    List<UserResponseDTO> getUsersByType(UserType userType);
}
