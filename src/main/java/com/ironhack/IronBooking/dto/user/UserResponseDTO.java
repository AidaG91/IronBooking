package com.ironhack.IronBooking.dto.user;
import com.ironhack.IronBooking.enums.UserType;

import lombok.Data;

// Send safe and relevant user data back to the client.
// Only exposes non-sensitive fields that should be visible to the API consumer.
@Data
public class UserResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private UserType userType;
}