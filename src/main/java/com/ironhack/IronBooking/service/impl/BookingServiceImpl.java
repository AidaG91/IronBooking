package com.ironhack.IronBooking.service.impl;

import com.ironhack.IronBooking.dto.BookingRequestDTO;
import com.ironhack.IronBooking.dto.BookingResponseDTO;
import com.ironhack.IronBooking.dto.BookingUpdateDTO;
import com.ironhack.IronBooking.enums.BookingStatus;
import com.ironhack.IronBooking.model.Booking;
import com.ironhack.IronBooking.repository.BookingRepository;
import com.ironhack.IronBooking.service.interfaces.BookingService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;

    public BookingServiceImpl(BookingRepository bookingRepository,
                              PlaceRepository placeRepository)
                              UserRepository userRepository) {
    this.bookingRepository = bookingRepository;
    this.placeRepository = placeRepository;
    this.userRepository = userRepository;
    }

    @Override
    public BookingResponseDTO createBooking(BookingRequestDTO dto) {
    if (dto.getEndDate().isBefore(dto.getStartDate())) {
        throw new IllegalArgumentException("endDate must be on or after startDate");
    }

    Place place = placeRepository.findById(dto.getPlaceId())
            .orElseThrow(() -> new EntityNotFoundException("Place not found with id: " + dto.getPlaceId()));

    User user = userRepository.findById(dto.getUserId())
            .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + dto.getUserId()));

    Booking booking = Booking.builder()
            .startDate(dto.getStartDate())
            .endDate(dto.getEndDate())
            .numberOfGuests(dto.getNumberOfGuests())
            .status(BookingStatus.CONFIRMED)
            .place(place)
            .user(user)
            .build();

    Booking saved = bookingRepository.save(booking);
    return toResponseDTO(saved);
    }

    // Read one
    @Override
    public  BookingResponseDTO getBookingById(Long id) {
      Booking booking = bookingRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Booking not found with id:" + id));
      return toResponseDTO(booking);
    }

    // Read all
    @Override
    public List<BookingResponseDTO> getAllBookings(){
        return bookingRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Update
    @Override
    public BookingResponseDTO updateBooking(Long id, BookingUpdateDTO dto){
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + id));

        if (dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new IllegalArgumentException("endDate must be on or after startDate");
        }

        Place place = placeRepository.findById(dto.getPlaceId())
                .orElseThrow(() -> new EntityNotFoundException("Place not found with id: " + dto.getPlaceId()));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + dto.getUserId()));

        booking.setStartDate(dto.getStartDate());
        booking.setEndDate(dto.getEndDate());
        booking.setNumberOfGuests(dto.getNumberOfGuests());
        booking.setStatus(dto.getStatus());
        booking.setPlace(place);
        booking.setUser(user);

        Booking updated = bookingRepository.save(booking);
        return toResponseDTO(updated);
    }

    // Delete
    @Override
    public void deleteBooking(Long id) {
    bookingRepository.deleteById(id);
    }

    // Read by status
    public List<BookingResponseDTO> getBookingByStatus(BookingStatus status) {
       return bookingRepository.findByStatus(status)
               .stream()
               .map(this::toResponseDTO)
               .toList();
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
