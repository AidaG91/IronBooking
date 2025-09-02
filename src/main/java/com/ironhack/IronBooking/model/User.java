package com.ironhack.IronBooking.model;
import com.ironhack.IronBooking.enums.UserType;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users", uniqueConstraints = {
@UniqueConstraint(columnNames = "email")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 60, message = "First name cannot exceed 60 characters.")
    @Column(nullable = false, length = 60)
    private String firstName;

    @NotBlank
    @Size(max = 60, message = "Last name cannot exceed 60 characters.")
    @Column(nullable = false, length = 60)
    private String lastName;

    @NotBlank(message = "Email cannot be blank.")
    @Email(message = "Email must be a valid email address.")
    @Size(max = 150, message = "Email cannot exceed 150 characters.")
    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @NotBlank(message = "Password cannot be blank.")
    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters.")
    @Column(nullable = false, length = 255)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private UserType userType;

    // 1:N User <---> Booking
    // CascadeType.ALL → if the user is deleted, all their bookings are also deleted
    // orphanRemoval → removes bookings that are no longer associated with any user
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings = new ArrayList<>();
}