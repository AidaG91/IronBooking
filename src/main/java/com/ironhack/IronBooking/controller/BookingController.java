package com.ironhack.IronBooking.controller;

import com.ironhack.IronBooking.dto.booking.BookingRequestDTO;
import com.ironhack.IronBooking.dto.booking.BookingResponseDTO;
import com.ironhack.IronBooking.dto.booking.BookingUpdateDTO;
import com.ironhack.IronBooking.enums.BookingStatus;
import com.ironhack.IronBooking.service.interfaces.BookingService;
import io.swagger.v3.oas.annotations.Operation;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new booking",
            description = "Create a new booking for a place and return the created booking.")
    public BookingResponseDTO createBooking(@Valid @RequestBody BookingRequestDTO requestDTO) {
        return bookingService.createBooking(requestDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get booking by ID",
            description = "Retrieve the details of a booking by its unique ID.")
    public BookingResponseDTO getBookingById(@PathVariable Long id){
        return bookingService.getBookingById(id);
    }

    @GetMapping
    @Operation(summary = "Get all bookings",
            description = "Retrieve a list of all bookings in the system.")
    public List<BookingResponseDTO> getALLBookings(){
        return  bookingService.getAllBookings();
    }

    @GetMapping("/status")
    @Operation(summary = "Get bookings by status",
            description = "Retrieve bookings filtered by their status (e.g., PENDING, CONFIRMED, CANCELLED).")
    public List<BookingResponseDTO> getBookingByStatus(@RequestParam BookingStatus status) {
        return bookingService.getBookingByStatus(status);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update booking by ID",
            description = "Update the details of an existing booking by its unique ID.")
    public BookingResponseDTO updateBooking(@PathVariable Long id,
                                            @Valid @RequestBody BookingUpdateDTO updateDTO) {
        return  bookingService.updateBooking(id, updateDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete booking by ID",
            description = "Delete a booking from the system by its unique ID.")
    public void deleteBooking(@PathVariable Long id){
        bookingService.deleteBooking(id);
    }
}
