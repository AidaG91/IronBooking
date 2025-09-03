package com.ironhack.IronBooking.repository;

import com.ironhack.IronBooking.enums.BookingStatus;
import com.ironhack.IronBooking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByStatus(BookingStatus status);

    @Query("""
        SELECT COUNT(b) FROM Booking b
        WHERE b.place.id = :placeId
          AND b.status = com.ironhack.IronBooking.enums.BookingStatus.CONFIRMED
          AND (b.startDate < :endDate AND b.endDate > :startDate)
    """)
    long countOverlaps(
            @Param("placeId") Long placeId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("""
        SELECT COUNT(b) FROM Booking b
        WHERE b.place.id = :placeId
          AND b.status = com.ironhack.IronBooking.enums.BookingStatus.CONFIRMED
          AND (b.startDate < :endDate AND b.endDate > :startDate)
          AND b.id <> :bookingId
    """)
    long countOverlapsExcludingId(
            @Param("placeId") Long placeId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("bookingId") Long bookingId
    );
}