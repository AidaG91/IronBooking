package com.ironhack.IronBooking.controller;

import com.ironhack.IronBooking.dto.place.*;
import com.ironhack.IronBooking.enums.PlaceType;
import com.ironhack.IronBooking.service.interfaces.PlaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/places")
@RequiredArgsConstructor
public class PlacesController {

    private final PlaceService service;

    @GetMapping
    public Page<PlaceResponseDTO> list(Pageable pageable) {
        return service.list(pageable);
    }

    @GetMapping("/{id}")
    public PlaceResponseDTO get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping("/type/{type}")
    public Page<PlaceResponseDTO> byType(@PathVariable PlaceType type, Pageable pageable) {
        return service.listByType(type, pageable);
    }

    @GetMapping("/city/{city}")
    public Page<PlaceResponseDTO> byCity(@PathVariable String city, Pageable pageable) {
        return service.listByCity(city, pageable);
    }

    @GetMapping("/country/{country}")
    public Page<PlaceResponseDTO> byCountry(@PathVariable String country, Pageable pageable) {
        return service.listByCountry(country, pageable);
    }

    @GetMapping("/capacity/{min}")
    public Page<PlaceResponseDTO> byMinCapacity(@PathVariable int min, Pageable pageable) {
        return service.listByMinCapacity(min, pageable);
    }

    @PostMapping
    public ResponseEntity<PlaceResponseDTO> create(@Valid @RequestBody PlaceRequestDTO req) {
        PlaceResponseDTO created = service.create(req);
        return ResponseEntity
                .created(URI.create("/places/" + created.getId()))
                .body(created);
    }

    @PutMapping("/{id}")
    public PlaceResponseDTO update(@PathVariable Long id, @Valid @RequestBody PlaceUpdateDTO req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
