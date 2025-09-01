package com.ironhack.IronBooking.dto;

import com.ironhack.IronBooking.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponseDTO {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private int numberOfGuests;
    private BookingStatus status;
    private Long placeId;
    private Long userId;
}
