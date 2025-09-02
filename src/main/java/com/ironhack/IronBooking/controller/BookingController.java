package com.ironhack.IronBooking.controller;

import com.ironhack.IronBooking.dto.booking.BookingRequestDTO;
import com.ironhack.IronBooking.dto.booking.BookingResponseDTO;
import com.ironhack.IronBooking.dto.booking.BookingUpdateDTO;
import com.ironhack.IronBooking.enums.BookingStatus;
import com.ironhack.IronBooking.service.interfaces.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // Create booking
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponseDTO createBooking(@Valid @RequestBody BookingRequestDTO requestDTO) {
        return bookingService.createBooking(requestDTO);
    }

    // Get a booking by ID
    @GetMapping("/{id}")
    public BookingResponseDTO getBookingById(@PathVariable Long id){
        return bookingService.getBookingById(id);
    }

    // Get all bookings
    @GetMapping
    public List<BookingResponseDTO> getALLBookings(){
        return  bookingService.getAllBookings();
    }

    //Get bookings by status
    public List<BookingResponseDTO> getBookingByStatus(@PathVariable BookingStatus status) {
        return bookingService.getBookingByStatus(status);
    }

    //Update a booking
    @PutMapping("/{id}")
    public BookingResponseDTO updateBooking(@PathVariable Long id,
                                            @Valid @RequestBody BookingUpdateDTO updateDTO) {
        return  bookingService.updateBooking(id, updateDTO);
    }

    // Delete a booking by ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBooking(@PathVariable Long id){
        bookingService.deleteBooking(id);
    }
}
