package com.ironhack.IronBooking.controller;

import com.ironhack.IronBooking.dto.place.*;
import com.ironhack.IronBooking.enums.PlaceType;
import com.ironhack.IronBooking.service.interfaces.PlaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/places")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService service;

    @GetMapping
    public List<PlaceResponseDTO> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public PlaceResponseDTO get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping("/type/{type}")
    public List<PlaceResponseDTO> byType(@PathVariable PlaceType type) {
        return service.listByType(type);
    }

    @GetMapping("/city/{city}")
    public List<PlaceResponseDTO> byCity(@PathVariable String city) {
        return service.listByCity(city);
    }

    @GetMapping("/country/{country}")
    public List<PlaceResponseDTO> byCountry(@PathVariable String country) {
        return service.listByCountry(country);
    }

    @GetMapping("/capacity/{min}")
    public List<PlaceResponseDTO> byMinCapacity(@PathVariable int min) {
        return service.listByMinCapacity(min);
    }

    @GetMapping("/owner/{ownerId}")
    public List<PlaceResponseDTO> byOwner(@PathVariable Long ownerId) {
        return service.listByOwner(ownerId);
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
