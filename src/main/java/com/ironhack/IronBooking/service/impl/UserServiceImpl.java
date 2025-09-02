package com.ironhack.IronBooking.service.impl;
import com.ironhack.IronBooking.dto.user.*;
import com.ironhack.IronBooking.dto.booking.*;
import com.ironhack.IronBooking.enums.UserType;
import com.ironhack.IronBooking.model.Booking;
import com.ironhack.IronBooking.model.User;

import com.ironhack.IronBooking.repository.UserRepository;
import com.ironhack.IronBooking.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    // Find user by ID and return as response DTO, or throw exception if not found
    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return toResponseDTO(user);
    }

    // Finds all users with the specified type (CLIENT/OWNER) and returns as a list of response DTOs
    @Override
    public List<UserResponseDTO> getUsersByType(UserType userType) {
        return userRepository.findByUserType(userType)
                .stream()
                .map(this::toResponseDTO) // Usa tu método de mapeo a DTO
                .collect(Collectors.toList());
    }

    // Return all users as a list of response DTOs
    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Create a new user from the request DTO and return a response DTO
    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        // Map request DTO to User entity
        User user = new User();
        user.setFirstName(userRequestDTO.getFirstName());
        user.setLastName(userRequestDTO.getLastName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPasswordHash(userRequestDTO.getPassword());
        user.setUserType(userRequestDTO.getUserType());

        // Save user in the database
        User savedUser = userRepository.save(user);

        // Convert saved user entity to response DTO
        return toResponseDTO(savedUser);
    }

    // Update user with fields present in the update DTO, then return updated user as response DTO
    @Override
    public UserResponseDTO updateUser(Long id, UserUpdateDTO userUpdateDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // Only update fields that are not null
        if (userUpdateDTO.getFirstName() != null)
            user.setFirstName(userUpdateDTO.getFirstName());
        if (userUpdateDTO.getLastName() != null)
            user.setLastName(userUpdateDTO.getLastName());
        if (userUpdateDTO.getEmail() != null)
            user.setEmail(userUpdateDTO.getEmail());
        if (userUpdateDTO.getPassword() != null)
            user.setPasswordHash(userUpdateDTO.getPassword());
        if (userUpdateDTO.getUserType() != null)
            user.setUserType(userUpdateDTO.getUserType());

        // Save updated user to the database
        User updatedUser = userRepository.save(user);

        // Convert updated user entity to response DTO
        return toResponseDTO(updatedUser);
    }

    // Delete user by ID (throw exception if not found)
    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id))
            throw new RuntimeException("User not found with id: " + id);
        userRepository.deleteById(id);
    }

    // Find user by email and return as response DTO, or throw exception if not found
    @Override
    public UserResponseDTO getUserByEmail(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        User user = userOpt.orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        return toResponseDTO(user);
    }

    // Helper method to convert User entity to UserResponseDTO
    private UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setUserType(user.getUserType());
        return dto;
    }

    // Get bookings for a user and return them as BookingResponseDTOs using the BookingResponseDTO constructor
    @Override
    public List<BookingResponseDTO> getBookingsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        return user.getBookings()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    private BookingResponseDTO toResponseDTO(Booking booking) {
        return new BookingResponseDTO(
                booking.getId(),
                booking.getStartDate(),
                booking.getEndDate(),
                booking.getNumberOfGuests(),
                booking.getStatus(),
                booking.getPlace() != null ? booking.getPlace().getId() : null,
                booking.getUser() != null ? booking.getUser().getId() : null
        );
    }
}
