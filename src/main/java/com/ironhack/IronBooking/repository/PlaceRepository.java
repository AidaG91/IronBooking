package com.ironhack.IronBooking.repository;

import com.ironhack.IronBooking.enums.PlaceType;
import com.ironhack.IronBooking.model.place.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    Page<Place> findByPlaceType(PlaceType type, Pageable pageable);
    Page<Place> findByCapacityGreaterThanEqual(Integer capacity, Pageable pageable);
    Page<Place> findByAddress_CityIgnoreCase(String city, Pageable pageable);
    Page<Place> findByAddress_CountryIgnoreCase(String country, Pageable pageable);
}
