package com.ironhack.IronBooking.dto.user;
import com.ironhack.IronBooking.enums.UserType;

import jakarta.validation.constraints.*;
import lombok.*;

// Receive and validate user data from the client when creating a new user.
@Data
public class UserRequestDTO {

    // Validation messages are handled here in the DTO, not in the entity/model.
    // Provide clear feedback to the API consumer.
    @NotBlank(message = "First name cannot be blank.")
    @Size(max = 60, message = "First name cannot exceed 60 characters.")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank.")
    @Size(max = 60, message = "Last name cannot exceed 60 characters.")
    private String lastName;

    @NotBlank(message = "Email cannot be blank.")
    @Email(message = "Email must be a valid email address.")
    @Size(max = 150, message = "Email cannot exceed 150 characters.")
    private String email;

    @NotBlank(message = "Password cannot be blank.")
    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters.")
    private String password;

    @Size(max = 10, message = "User type cannot exceed 10 characters.")
    private UserType userType;
}
