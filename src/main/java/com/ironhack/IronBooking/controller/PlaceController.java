package com.ironhack.IronBooking.controller;

import com.ironhack.IronBooking.dto.place.*;
import com.ironhack.IronBooking.enums.PlaceType;
import com.ironhack.IronBooking.service.interfaces.PlaceService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Get all places",
            description = "Retrieve a list of all places available for booking.")
    public List<PlaceResponseDTO> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get place by ID",
            description = "Retrieve details for a specific place by its unique ID.")
    public PlaceResponseDTO get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping("/type/{type}")
    @Operation(summary = "Get places by type",
            description = "Retrieve a list of places filtered by their type.")
    public List<PlaceResponseDTO> byType(@PathVariable PlaceType type) {
        return service.listByType(type);
    }

    @GetMapping("/city/{city}")
    @Operation(summary = "Get places by city",
            description = "Retrieve a list of places located in a specific city.")
    public List<PlaceResponseDTO> byCity(@PathVariable String city) {
        return service.listByCity(city);
    }

    @GetMapping("/country/{country}")
    @Operation(summary = "Get places by country",
            description = "Retrieve a list of places located in a specific country.")
    public List<PlaceResponseDTO> byCountry(@PathVariable String country) {
        return service.listByCountry(country);
    }

    @GetMapping("/capacity/{min}")
    @Operation(summary = "Get places by minimum capacity",
            description = "Retrieve a list of places that have at least the specified minimum capacity.")
    public List<PlaceResponseDTO> byMinCapacity(@PathVariable int min) {
        return service.listByMinCapacity(min);
    }

    @GetMapping("/owner/{ownerId}")
    @Operation(summary = "Get places by owner",
            description = "Retrieve a list of places managed by a specific owner.")
    public List<PlaceResponseDTO> byOwner(@PathVariable Long ownerId) {
        return service.listByOwner(ownerId);
    }

    @PostMapping
    @Operation(summary = "Create a new place",
            description = "Add a new place to the system and return the created place with a Location header.")
    public ResponseEntity<PlaceResponseDTO> create(@Valid @RequestBody PlaceRequestDTO req) {
        PlaceResponseDTO created = service.create(req);
        return ResponseEntity
                .created(URI.create("/places/" + created.getId()))
                .body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update place by ID",
            description = "Update the details of an existing place by its unique ID.")
    public PlaceResponseDTO update(@PathVariable Long id, @Valid @RequestBody PlaceUpdateDTO req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete place by ID",
            description = "Delete a place from the system by its unique ID.")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
