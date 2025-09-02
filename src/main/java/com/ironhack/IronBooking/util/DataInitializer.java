package com.ironhack.IronBooking.util;
import com.ironhack.IronBooking.enums.*;
import com.ironhack.IronBooking.model.*;
import com.ironhack.IronBooking.repository.*;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    // Añade aquí los repositorios cuando estén implementados:
    private final UserRepository userRepository;
    // private final BookingRepository bookingRepository;
    // private final PlaceRepository placeRepository;
    // private final ReviewRepository reviewRepository;

    @Override
    public void run(String... args) {
        // Only insert sample data if the database is empty (users)
        if (userRepository.count() == 0) {
            // -----------------------------
            // 1 - Create Users
            // -----------------------------
            User user1 = User.builder()
                    .firstName("Alice")
                    .lastName("Smith")
                    .email("alice@mail.com")
                    .passwordHash("password123")
                    .userType(UserType.CLIENT)
                    .build();

            User user2 = User.builder()
                    .firstName("Bob")
                    .lastName("Brown")
                    .email("bob@mail.com")
                    .passwordHash("password456")
                    .userType(UserType.OWNER)
                    .build();

            User user3 = User.builder()
                    .firstName("Charlie")
                    .lastName("Davis")
                    .email("charlie@mail.com")
                    .passwordHash("password789")
                    .userType(UserType.CLIENT)
                    .build();

            userRepository.saveAll(List.of(user1, user2, user3));
            System.out.println("+++++++++++++++ Sample users inserted correctly into the database.");

            // 2 - Create Places
            //     // TODO: Implement sample places, relate with users (owners)
            //     // Place place1 = Place.builder()...build();
            //     // placeRepository.saveAll(List.of(place1, ...));
            //     System.out.println("+++++++++++++++ Sample places inserted correctly into the database.");

            // 3 - Create Bookings
            //     // TODO: Implement sample bookings, relate with users and places
            //     // Booking booking1 = Booking.builder()...build();
            //     // bookingRepository.saveAll(List.of(booking1, ...));
            //     System.out.println("+++++++++++++++ Sample bookings inserted correctly into the database.");

            // 4 - Create Reviews
            //     // TODO: Implement sample reviews, relate with users (author), bookings/places
            //     // Review review1 = Review.builder()...build();
            //     // reviewRepository.saveAll(List.of(review1, ...));
            //     System.out.println("+++++++++++++++ Sample reviews inserted correctly into the database.");
        }
    }
}
