package com.ironhack.IronBooking.service.interfaces;

import com.ironhack.IronBooking.model.Booking;

public interface BookingService {
    Booking createBooking(Booking booking);
    Booking getBookingById(Long id);
}
