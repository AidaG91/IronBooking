package com.ironhack.IronBooking.dto.booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ironhack.IronBooking.enums.BookingStatus;
import com.ironhack.IronBooking.model.Booking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponseDTO {
    private Long id;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate startDate;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate endDate;
    private int numberOfGuests;
    private BookingStatus status;
    private Long placeId;
    private Long userId;

}
