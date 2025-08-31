package com.ironhack.IronBooking.service.impl;

import com.ironhack.IronBooking.dto.BookingRequestDTO;
import com.ironhack.IronBooking.dto.BookingResponseDTO;
import com.ironhack.IronBooking.enums.BookingStatus;
import com.ironhack.IronBooking.model.Booking;
import com.ironhack.IronBooking.repository.BookingRepository;
import com.ironhack.IronBooking.service.interfaces.BookingService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService{

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Override
    public BookingResponseDTO getBookingById(Long  id){
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + id));

        return new BookingResponseDTO(
                booking.getId(),
                booking.getDate(),
                booking.getStatus(),
                booking.getNumberOfGuests(),
                booking.getPlace().getId()
        );
    }

    @Override
    public BookingResponseDTO createBooking(BookingRequestDTO requestDTO) {
        Booking booking = new Booking(
                requestDTO.getDate(),
                BookingStatus.CONFIRMED,
                requestDTO.getNumberOfGuests(),
                new Place(requestDTO.getPlaceId())
        );
        Booking savedBooking = bookingRepository.save(booking);
        return new BookingResponseDTO(
                savedBooking.getId(),
                savedBooking.getDate(),
                savedBooking.getStatus(),
                savedBooking.getNumberOfGuests(),
                savedBooking.getPlace().getId()
        );
    }

    @Override
    public List<BookingResponseDTO> getAllBookings(){
        return bookingRepository.findAll()
                .stream()
                .map(b -> new BookingResponseDTO(
                        b.getId(),
                        b.getDate(),
                        b.getStatus(),
                        b.getNumberOfGuests(),
                        b.getPlace().getId()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public BookingResponseDTO updateBooking(Long id, BookingRequestDTO requestDTO) {
        Booking booking = bookingRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Booking is not found with id: " + id));

        booking.setDate(requestDTO.getDate());
        booking.setNumberOfGuests(requestDTO.getNumberOfGuests());
        booking.setPlace(new Place(requestDTO.getPlaceId()));

        Booking updateBooking = bookingRepository.save(booking);

        return new BookingResponseDTO(
                updateBooking.getId(),
                updateBooking.getDate(),
                updateBooking.getStatus(),
                updateBooking.getNumberOfGuests(),
                updateBooking.getPlace().getId()
        );
    }

    @Override
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}
