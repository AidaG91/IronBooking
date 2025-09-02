package com.ironhack.IronBooking.util;

import com.ironhack.IronBooking.enums.BookingStatus;
import com.ironhack.IronBooking.model.Booking;
import com.ironhack.IronBooking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) {

        if (bookingRepository.count() == 0) {
            User user1 = userRepository.findById(1L)
                    .orElseThrow(() -> new RuntimeException("User with id 1 not found"));
            User user2 = userRepository.findById(2L)
                    .orElseThrow(() -> new RuntimeException("User with id 2 not found"));
            User user3 = userRepository.findById(3L)
                    .orElseThrow(() -> new RuntimeException("User with id 3 not found");

            Place place1 = placeRepository.findById(1L)
                    .orElseThrow(() -> new RuntimeException("Place with id 1 not found"));
            Place place2 = placeRepository.findById(2L)
                    .orElseThrow(() -> new RuntimeException("Place with id 2 not found"));
            Place place3 = placeRepository.findById(3L)
                    .orElseThrow(() -> new RuntimeException("Place with id 3 not found"));

            // Booking for 4 nights (tomorrow -> 5 days)
            Booking booking1 = Booking.builder()
                    .startDate(LocalDate.now().plusDays(1))
                    .endDate(LocalDate.now().plusDays(5))
                    .numberOfGuests(2)
                    .status(BookingStatus.CONFIRMED)
                    .user(user1)
                    .place(place1)
                    .build();

            // Booking for the weekend (within 10 days)
            Booking booking2 = Booking.builder()
                    .startDate(LocalDate.now().plusDays(10))
                    .endDate(LocalDate.now().plusDays(12))
                    .numberOfGuests(4)
                    .status(BookingStatus.CONFIRMED)
                    .user(user1)
                    .place(place1)
                    .build();

            // Booking cancelled (3 days range)
            Booking booking3 = Booking.builder()
                    .startDate(LocalDate.now().plusDays(4))
                    .endDate(LocalDate.now().plusDays(7))
                    .numberOfGuests(6)
                    .status(BookingStatus.CANCELLED)
                    .user(user1)
                    .place(place1)
                    .build();

            bookingRepository.saveAll(List.of(booking1,booking2,booking3));
        }
    }
}
