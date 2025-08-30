package com.ironhack.IronBooking.dto;

import com.ironhack.IronBooking.enums.BookingStatus;

import java.time.LocalDate;

public class BookingDTO {
    private Long id;
    private LocalDate date;
    private BookingStatus status;
    private int numberOfGuests;
    private Long placedId;

    public BookingDTO() {}

    public BookingDTO(Long id, LocalDate date, BookingStatus status, int numberOfGuests, Long placedId) {
        this.id = id;
        this.date = date;
        this.status = status;
        this.numberOfGuests = numberOfGuests;
        this.placedId = placedId;
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

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public Long getPlacedId() {
        return placedId;
    }

    public void setPlacedId(Long placedId) {
        this.placedId = placedId;
    }
}
