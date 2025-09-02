package com.ironhack.IronBooking.service.interfaces;

import com.ironhack.IronBooking.dto.BookingRequestDTO;
import com.ironhack.IronBooking.dto.BookingResponseDTO;
import com.ironhack.IronBooking.dto.BookingUpdateDTO;
import com.ironhack.IronBooking.enums.BookingStatus;

import java.util.List;

public interface BookingService {

    BookingResponseDTO createBooking(BookingRequestDTO requestDTO);

    BookingResponseDTO getBookingById(Long id);

    List<BookingResponseDTO> getAllBookings();

    BookingResponseDTO updateBooking(Long id, BookingUpdateDTO updateDTO);

    void deleteBooking(Long id);

    List<BookingResponseDTO> getBookingByStatus(BookingStatus status);
}
