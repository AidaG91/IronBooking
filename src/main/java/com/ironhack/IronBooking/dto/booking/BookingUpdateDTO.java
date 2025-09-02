package com.ironhack.IronBooking.dto.booking;

import com.ironhack.IronBooking.enums.BookingStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingUpdateDTO {

    @NotNull(message = "id is required")
    private Long id;

    @NotNull(message = "startDate is required")
    @FutureOrPresent(message = "startDate must be today or in the future")
    private LocalDate startDate;

    @NotNull(message = "endDate is required")
    @FutureOrPresent(message = "endDate must be today or in the future")
    private LocalDate endDate;

    @NotNull(message = "Status is required")
    private BookingStatus status;

    @Min(value = 1, message = "numberOfGuests must be at least 1")
    @Max(value = 20, message = "numberOfGuests must be at least 20")
    private int numberOfGuests;

    @NotNull(message = "placeId is required")
    private Long placeId;

    @NotNull(message = "userId is required")
    private Long userId;
}
