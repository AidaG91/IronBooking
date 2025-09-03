package com.ironhack.IronBooking.service.impl;

import com.ironhack.IronBooking.dto.booking.BookingRequestDTO;
import com.ironhack.IronBooking.dto.booking.BookingResponseDTO;
import com.ironhack.IronBooking.dto.booking.BookingUpdateDTO;
import com.ironhack.IronBooking.enums.BookingStatus;
import com.ironhack.IronBooking.model.Booking;
import com.ironhack.IronBooking.model.User;
import com.ironhack.IronBooking.model.place.Place;
import com.ironhack.IronBooking.repository.BookingRepository;
import com.ironhack.IronBooking.repository.PlaceRepository;
import com.ironhack.IronBooking.repository.UserRepository;
import com.ironhack.IronBooking.service.interfaces.BookingService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public BookingResponseDTO createBooking(@Valid BookingRequestDTO dto) {
        validateDates(dto.getStartDate(), dto.getEndDate()); // [start, end)

        Place place = placeRepository.findById(dto.getPlaceId())
                .orElseThrow(() -> new EntityNotFoundException("Place not found with id: " + dto.getPlaceId()));
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + dto.getUserId()));

        // Availability check (CONFIRMED only)
        long conflicts = bookingRepository.countOverlaps(place.getId(), dto.getStartDate(), dto.getEndDate());
        if (conflicts > 0) {
            throw new IllegalStateException("Place is not available for the selected dates");
        }

        Booking booking = Booking.builder()
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .numberOfGuests(dto.getNumberOfGuests())
                .status(BookingStatus.CONFIRMED) // keep confirmed for study project
                .place(place)
                .user(user)
                .build();

        return toResponseDTO(bookingRepository.save(booking));
    }

    @Override
    @Transactional(readOnly = true)
    public BookingResponseDTO getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id:" + id));
        return toResponseDTO(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingResponseDTO> getAllBookings() {
        return bookingRepository.findAll().stream().map(this::toResponseDTO).toList();
    }

    @Override
    @Transactional
    public BookingResponseDTO updateBooking(Long id, @Valid BookingUpdateDTO dto) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + id));

        validateDates(dto.getStartDate(), dto.getEndDate()); // [start, end)

        Place place = placeRepository.findById(dto.getPlaceId())
                .orElseThrow(() -> new EntityNotFoundException("Place not found with id: " + dto.getPlaceId()));
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + dto.getUserId()));

        // Availability check ignoring the current booking's id
        long conflicts = bookingRepository.countOverlapsExcludingId(
                place.getId(), dto.getStartDate(), dto.getEndDate(), booking.getId());
        if (conflicts > 0) {
            throw new IllegalStateException("Place is not available for the selected dates");
        }

        booking.setStartDate(dto.getStartDate());
        booking.setEndDate(dto.getEndDate());
        booking.setNumberOfGuests(dto.getNumberOfGuests());
        booking.setStatus(dto.getStatus());
        booking.setPlace(place);
        booking.setUser(user);

        return toResponseDTO(bookingRepository.save(booking));
    }

    @Transactional
    public void deleteBooking(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new EntityNotFoundException("Booking not found with id: " + id);
        }
        bookingRepository.deleteById(id);
    }


    @Transactional(readOnly = true)
    public List<BookingResponseDTO> getBookingByStatus(BookingStatus status) {
        return bookingRepository.findByStatus(status).stream().map(this::toResponseDTO).toList();
    }

    private void validateDates(LocalDate start, LocalDate end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("startDate and endDate are required");
        }
        if (!end.isAfter(start)) {
            throw new IllegalArgumentException("endDate must be after startDate (exclusive end semantics)");
        }
    }

    private BookingResponseDTO toResponseDTO(Booking booking) {
        return new BookingResponseDTO(
                booking.getId(),
                booking.getStartDate(),
                booking.getEndDate(),
                booking.getNumberOfGuests(),
                booking.getStatus(),
                booking.getPlace() != null ? booking.getPlace().getId() : null,
                booking.getUser() != null ? booking.getUser().getId() : null
        );
    }
}
