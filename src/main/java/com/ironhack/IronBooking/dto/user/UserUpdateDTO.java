package com.ironhack.IronBooking.dto.user;
import com.ironhack.IronBooking.enums.UserType;

import jakarta.validation.constraints.*;
import lombok.Data;

// Receive and validate user data from the client when updating an existing user.
// Only updatable fields are included; validation is adapted for partial updates if necessary.
@Data
public class UserUpdateDTO {

    // Validation messages are handled here in the DTO, not in the entity/model.
    // Provide clear feedback to the API consumer.
    @Size(max = 60, message = "First name cannot exceed 60 characters.")
    private String firstName;

    @Size(max = 60, message = "Last name cannot exceed 60 characters.")
    private String lastName;

    @Email(message = "Email must be a valid email address.")
    @Size(max = 150, message = "Email cannot exceed 150 characters.")
    private String email;

    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters.")
    private String password;

    private UserType userType;
}
