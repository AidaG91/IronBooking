package com.ironhack.IronBooking.service.interfaces;

import com.ironhack.IronBooking.dto.place.*;
import com.ironhack.IronBooking.enums.PlaceType;

import java.util.List;

public interface PlaceService {
    PlaceResponseDTO create(PlaceRequestDTO req);
    PlaceResponseDTO get(Long id);
    List<PlaceResponseDTO> list();
    List<PlaceResponseDTO> listByType(PlaceType type);
    List<PlaceResponseDTO> listByCity(String city);
    List<PlaceResponseDTO> listByCountry(String country);
    List<PlaceResponseDTO> listByMinCapacity(int capacity);
    PlaceResponseDTO update(Long id, PlaceUpdateDTO req);
    void delete(Long id);
}
