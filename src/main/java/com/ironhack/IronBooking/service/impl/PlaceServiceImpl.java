package com.ironhack.IronBooking.service.impl;

import com.ironhack.IronBooking.dto.place.*;
import com.ironhack.IronBooking.enums.PlaceType;
import com.ironhack.IronBooking.enums.UserType;
import com.ironhack.IronBooking.model.User;
import com.ironhack.IronBooking.model.place.Place;
import com.ironhack.IronBooking.repository.PlaceRepository;
import com.ironhack.IronBooking.repository.UserRepository;
import com.ironhack.IronBooking.service.interfaces.PlaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository repo;
    private final UserRepository userRepository;

    @Override @Transactional
    public PlaceResponseDTO create(@Valid PlaceRequestDTO req) {
        // resolve and validate owner
        User owner = userRepository.findById(req.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Owner not found: " + req.getOwnerId()));
        if (owner.getUserType() != UserType.OWNER) {
            throw new RuntimeException("User " + req.getOwnerId() + " is not an OWNER");
        }

        Place p = Place.builder()
                .placeType(req.getPlaceType())
                .capacity(req.getCapacity())
                .build();
        p.setName(req.getName());
        p.setAddress(req.getAddress());
        p.setPrice(req.getPrice());
        p.setOwner(owner);

        return toDto(repo.save(p));
    }

    @Override @Transactional(readOnly = true)
    public PlaceResponseDTO get(Long id) {
        return repo.findById(id).map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Place not found: " + id));
    }

    @Override @Transactional(readOnly = true)
    public List<PlaceResponseDTO> list() {
        return repo.findAll().stream().map(this::toDto).toList();
    }

    @Override @Transactional(readOnly = true)
    public List<PlaceResponseDTO> listByType(PlaceType type) {
        return repo.findByPlaceType(type).stream().map(this::toDto).toList();
    }

    @Override @Transactional(readOnly = true)
    public List<PlaceResponseDTO> listByCity(String city) {
        return repo.findByAddress_CityIgnoreCase(city).stream().map(this::toDto).toList();
    }

    @Override @Transactional(readOnly = true)
    public List<PlaceResponseDTO> listByCountry(String country) {
        return repo.findByAddress_CountryIgnoreCase(country).stream().map(this::toDto).toList();
    }

    @Override @Transactional(readOnly = true)
    public List<PlaceResponseDTO> listByMinCapacity(int capacity) {
        return repo.findByCapacityGreaterThanEqual(capacity).stream().map(this::toDto).toList();
    }

    // list by owner
    @Override @Transactional(readOnly = true)
    public List<PlaceResponseDTO> listByOwner(Long ownerId) {
        return repo.findByOwner_Id(ownerId).stream().map(this::toDto).toList();
    }

    @Override @Transactional
    public PlaceResponseDTO update(Long id, @Valid PlaceUpdateDTO req) {
        Place p = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Place not found: " + id));

        if (req.getName() != null) p.setName(req.getName());
        if (req.getAddress() != null) p.setAddress(req.getAddress());
        if (req.getPrice() != null) p.setPrice(req.getPrice());
        if (req.getPlaceType() != null) p.setPlaceType(req.getPlaceType());
        if (req.getCapacity() != null) p.setCapacity(req.getCapacity());

        // reassign owner if provided
        if (req.getOwnerId() != null) {
            User newOwner = userRepository.findById(req.getOwnerId())
                    .orElseThrow(() -> new RuntimeException("Owner not found: " + req.getOwnerId()));
            if (newOwner.getUserType() != UserType.OWNER) {
                throw new RuntimeException("User " + req.getOwnerId() + " is not an OWNER");
            }
            p.setOwner(newOwner);
        }

        return toDto(repo.save(p));
    }

    @Override @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new RuntimeException("Place not found: " + id);
        repo.deleteById(id);
    }

    private PlaceResponseDTO toDto(Place p) {
        return PlaceResponseDTO.builder()
                .id(p.getId())
                .name(p.getName())
                .address(p.getAddress())
                .price(p.getPrice())
                .placeType(p.getPlaceType())
                .capacity(p.getCapacity())
                .ownerId(p.getOwner() != null ? p.getOwner().getId() : null)
                .build();
    }
}
