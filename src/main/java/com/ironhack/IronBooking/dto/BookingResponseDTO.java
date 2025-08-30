package com.ironhack.IronBooking.dto;

import com.ironhack.IronBooking.enums.BookingStatus;

import java.time.LocalDate;

public class BookingResponseDTO {
    private Long id;
    private LocalDate date;
    private int numberOfGuests;
    private BookingStatus status;

    public BookingResponseDTO() {}

    public BookingResponseDTO (Long id, LocalDate date, int numberOfGuests, BookingStatus status) {
        this.id = id;
        this.date = date;
        this.numberOfGuests = numberOfGuests;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }
}
