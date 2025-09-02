package com.ironhack.IronBooking.dto.place;

import com.ironhack.IronBooking.enums.PlaceType;
import com.ironhack.IronBooking.model.vo.Address;
import com.ironhack.IronBooking.model.vo.Price;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PlaceUpdateDTO {

    @Size(max = 80, message = "Name must be at most 80 characters.")
    private String name;

    @Valid
    private Address address;

    @Valid
    private Price price;

    private PlaceType placeType;

    @Positive(message = "Capacity must be greater than 0.")
    private Integer capacity;

    @Positive(message="Owner id must be positive.")
    private Long ownerId;
}
