package com.ironhack.IronBooking.service.interfaces;

import com.ironhack.IronBooking.dto.place.*;
import com.ironhack.IronBooking.enums.PlaceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlaceService {
    PlaceResponseDTO create(PlaceRequestDTO req);
    PlaceResponseDTO get(Long id);
    Page<PlaceResponseDTO> list(Pageable pageable);
    Page<PlaceResponseDTO> listByType(PlaceType type, Pageable pageable);
    Page<PlaceResponseDTO> listByCity(String city, Pageable pageable);
    Page<PlaceResponseDTO> listByCountry(String country, Pageable pageable);
    Page<PlaceResponseDTO> listByMinCapacity(int capacity, Pageable pageable);
    PlaceResponseDTO update(Long id, PlaceUpdateDTO req);
    void delete(Long id);
}
