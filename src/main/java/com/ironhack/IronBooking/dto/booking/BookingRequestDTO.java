package com.ironhack.IronBooking.dto.booking;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class BookingRequestDTO {

    @NotNull(message = "startDate is required")
    @FutureOrPresent(message = "startDate must be today or in the future")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate startDate;

    @NotNull(message = "endDate is required")
    @FutureOrPresent(message = "endDate must be today or in the future")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate endDate;

    @Min(value = 1, message = "numberOfGuests must be at least 1")
    @Max(value = 20, message = "numberOfGuests must be at most 20")
    private int numberOfGuests;

    @NotNull(message = "placeId is required")
    private Long placeId;

    @NotNull(message = "userId is required")
    private Long userId;
}
