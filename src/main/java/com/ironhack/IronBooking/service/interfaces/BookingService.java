package com.ironhack.IronBooking.service.interfaces;

import com.ironhack.IronBooking.dto.BookingRequestDTO;
import com.ironhack.IronBooking.dto.BookingResponseDTO;

import java.util.List;

public interface BookingService {
    BookingResponseDTO getBookingById(Long id);
    BookingResponseDTO createBooking(BookingRequestDTO requestDTO);
    List<BookingResponseDTO> getAllBookings();
    BookingResponseDTO updateBooking(Long id, BookingRequestDTO requestDTO);

    void deleteBooking(Long id);
}
