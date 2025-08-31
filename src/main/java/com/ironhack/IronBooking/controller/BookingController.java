package com.ironhack.IronBooking.controller;

import com.ironhack.IronBooking.dto.BookingRequestDTO;
import com.ironhack.IronBooking.dto.BookingResponseDTO;
import com.ironhack.IronBooking.model.Booking;
import com.ironhack.IronBooking.service.interfaces.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // Create booking
    @PostMapping
    public BookingResponseDTO createBooking(@RequestBody BookingRequestDTO requestDTO) {
        return bookingService.createBooking(requestDTO);
    }

    // Get a booking by ID
    @GetMapping("/{id}")
    public BookingResponseDTO getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

    // Get all bookings
    @GetMapping
    public List<BookingResponseDTO> getALLBookings(){
        return  bookingService.getAllBookings();
    }

    // Delete a booking by ID
    @DeleteMapping
    public void deleteBooking(@PathVariable Long id){
        bookingService.deleteBooking(id);
    }
}
