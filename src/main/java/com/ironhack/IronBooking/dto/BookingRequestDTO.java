package com.ironhack.IronBooking.dto;

import com.ironhack.IronBooking.enums.BookingStatus;

import java.time.LocalDate;

public class BookingRequestDTO {
    private LocalDate date;
    private int numberOfGuests;
    private BookingStatus status;
    private Long placeId;

    public BookingRequestDTO () {}

    public BookingRequestDTO(LocalDate date, int numberOfGuests, BookingStatus status, Long placeId) {
        this.date = date;
        this.numberOfGuests = numberOfGuests;
        this.status = status;
        this.placeId = placeId;
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

    public Long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }
}
