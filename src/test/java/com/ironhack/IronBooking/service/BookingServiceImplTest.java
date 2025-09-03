package com.ironhack.IronBooking.service;

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
import com.ironhack.IronBooking.service.impl.BookingServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit tests for BookingServiceImpl")
class BookingServiceImplTest {

    @Mock private BookingRepository bookingRepository;
    @Mock private PlaceRepository placeRepository;
    @Mock private UserRepository userRepository;

    @InjectMocks
    private BookingServiceImpl service;

    private Place place;
    private User user;

    private final LocalDate START = LocalDate.now().plusDays(10);
    private final LocalDate END   = START.plusDays(3); // 3 nights

    @BeforeEach
    void setUp() {
        place = new Place();
        place.setId(100L);

        user = new User();
        user.setId(200L);
    }

    private BookingRequestDTO req() {
        return new BookingRequestDTO(START, END, 2, place.getId(), user.getId());
    }

    private BookingUpdateDTO upd() {
        BookingUpdateDTO dto = new BookingUpdateDTO();
        dto.setStartDate(START.plusDays(1));
        dto.setEndDate(END.plusDays(1));
        dto.setNumberOfGuests(3);
        dto.setPlaceId(place.getId());
        dto.setUserId(user.getId());
        dto.setStatus(BookingStatus.CONFIRMED);
        return dto;
    }

    private Booking existingBooking(Long id) {
        return Booking.builder()
                .id(id)
                .startDate(START)
                .endDate(END)
                .numberOfGuests(2)
                .status(BookingStatus.CONFIRMED)
                .place(place)
                .user(user)
                .build();
    }

    // -------- createBooking --------

    @Test
    @DisplayName("createBooking should succeed when no overlapping booking exists")
    void createBooking_success_whenNoOverlap() {
        when(placeRepository.findById(place.getId())).thenReturn(Optional.of(place));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(bookingRepository.countOverlaps(place.getId(), START, END)).thenReturn(0L);
        when(bookingRepository.save(any(Booking.class)))
                .thenAnswer(inv -> { Booking b = inv.getArgument(0); b.setId(1L); return b; });

        BookingResponseDTO res = service.createBooking(req());

        assertNotNull(res);
        assertEquals(1L, res.getId());
        assertEquals(BookingStatus.CONFIRMED, res.getStatus());
    }

    @Test
    @DisplayName("createBooking should throw conflict when overlaps exist")
    void createBooking_throwsConflict_whenOverlapExists() {
        when(placeRepository.findById(place.getId())).thenReturn(Optional.of(place));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(bookingRepository.countOverlaps(place.getId(), START, END)).thenReturn(1L);

        assertThrows(IllegalStateException.class, () -> service.createBooking(req()));
    }

    @Test
    @DisplayName("createBooking should throw bad request when endDate is not after startDate")
    void createBooking_throwsBadRequest_whenEndNotAfterStart() {
        BookingRequestDTO bad = new BookingRequestDTO(START, START, 1, place.getId(), user.getId());

        assertThrows(IllegalArgumentException.class, () -> service.createBooking(bad));
    }

    @Test
    @DisplayName("createBooking should throw not found when place is missing")
    void createBooking_throwsNotFound_whenPlaceMissing() {
        when(placeRepository.findById(place.getId())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.createBooking(req()));
    }

    @Test
    @DisplayName("createBooking should throw not found when user is missing")
    void createBooking_throwsNotFound_whenUserMissing() {
        when(placeRepository.findById(place.getId())).thenReturn(Optional.of(place));
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.createBooking(req()));
    }

    // -------- updateBooking --------

    @Test
    @DisplayName("updateBooking should succeed when no overlapping booking exists (excluding current one)")
    void updateBooking_success_whenNoOverlapExcludingSelf() {
        Booking existing = existingBooking(9L);

        when(bookingRepository.findById(9L)).thenReturn(Optional.of(existing));
        when(placeRepository.findById(place.getId())).thenReturn(Optional.of(place));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(bookingRepository.countOverlapsExcludingId(place.getId(), START.plusDays(1), END.plusDays(1), 9L))
                .thenReturn(0L);
        when(bookingRepository.save(any(Booking.class))).thenAnswer(inv -> inv.getArgument(0));

        BookingResponseDTO res = service.updateBooking(9L, upd());

        assertNotNull(res);
        assertEquals(9L, res.getId());
        assertEquals(START.plusDays(1), res.getStartDate());
        assertEquals(END.plusDays(1), res.getEndDate());
    }

    @Test
    @DisplayName("updateBooking should throw conflict when overlapping with another booking")
    void updateBooking_throwsConflict_whenOverlapWithOtherBooking() {
        Booking existing = existingBooking(9L);

        when(bookingRepository.findById(9L)).thenReturn(Optional.of(existing));
        when(placeRepository.findById(place.getId())).thenReturn(Optional.of(place));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(bookingRepository.countOverlapsExcludingId(place.getId(), START.plusDays(1), END.plusDays(1), 9L))
                .thenReturn(2L);

        assertThrows(IllegalStateException.class, () -> service.updateBooking(9L, upd()));
    }

    @Test
    @DisplayName("updateBooking should throw bad request when endDate is not after startDate")
    void updateBooking_throwsBadRequest_whenEndNotAfterStart() {
        Booking existing = existingBooking(9L);
        BookingUpdateDTO bad = upd();
        bad.setEndDate(bad.getStartDate()); // invalid

        when(bookingRepository.findById(9L)).thenReturn(Optional.of(existing));

        assertThrows(IllegalArgumentException.class, () -> service.updateBooking(9L, bad));
    }

    // -------- get/delete --------

    @Test
    @DisplayName("getBookingById should throw not found when booking does not exist")
    void getBookingById_throwsNotFound_whenMissing() {
        when(bookingRepository.findById(123L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.getBookingById(123L));
    }

    @Test
    @DisplayName("deleteBooking should throw not found when booking does not exist")
    void deleteBooking_throwsNotFound_whenMissing() {
        when(bookingRepository.existsById(321L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.deleteBooking(321L));
    }

    @Test
    @DisplayName("deleteBooking should succeed when booking exists")
    void deleteBooking_success_whenExists() {
        when(bookingRepository.existsById(321L)).thenReturn(true);
        service.deleteBooking(321L);
        verify(bookingRepository).deleteById(321L);
    }
}
