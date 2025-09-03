package com.ironhack.IronBooking.dto.place;

import com.ironhack.IronBooking.enums.PlaceType;
import com.ironhack.IronBooking.model.vo.Address;
import com.ironhack.IronBooking.model.vo.Price;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PlaceRequestDTO {

    @NotBlank(message = "Name is required.")
    @Size(max = 80, message = "Name must be at most 80 characters.")
    private String name;

    @NotNull(message = "Address is required.")
    @Valid
    private Address address;

    @NotNull(message = "Price is required.")
    @Valid
    private Price price;

    @NotNull(message = "Place type is required.")
    private PlaceType placeType;

    @NotNull(message = "Capacity is required.")
    @Positive(message = "Capacity must be greater than 0.")
    private Integer capacity;

    @NotNull(message="Owner id is required.")
    @Positive(message="Owner id must be positive.")
    private Long ownerId;
}
