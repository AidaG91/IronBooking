package com.ironhack.IronBooking.repository;

import com.ironhack.IronBooking.enums.PlaceType;
import com.ironhack.IronBooking.model.place.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    List<Place> findByPlaceType(PlaceType type);
    List<Place> findByCapacityGreaterThanEqual(Integer capacity);
    List<Place> findByAddress_CityIgnoreCase(String city);
    List<Place> findByAddress_CountryIgnoreCase(String country);
    List<Place> findByOwner_Id(Long ownerId);
}
